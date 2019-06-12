package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.dao.api.IAuthorDAO;
import by.intexsoft.restlibrary.model.dao.api.IBookDAO;
import by.intexsoft.restlibrary.model.dto.BookDTO;
import by.intexsoft.restlibrary.model.enumeration.FileExtension;
import by.intexsoft.restlibrary.model.filter.BookFilter;
import by.intexsoft.restlibrary.service.api.IBookService;
import by.intexsoft.restlibrary.service.loader.CSVBookLoader;
import by.intexsoft.restlibrary.service.loader.ExcelBookLoader;
import by.intexsoft.restlibrary.service.loader.api.IBookLoader;
import by.intexsoft.restlibrary.service.parser.BookParser;
import by.intexsoft.restlibrary.service.reader.CSVReader;
import by.intexsoft.restlibrary.service.reader.ExcelReader;
import by.intexsoft.restlibrary.service.writer.CSVWriter;
import by.intexsoft.restlibrary.service.writer.api.ICSVWriter;
import by.intexsoft.restlibrary.util.DTOUtils;
import by.intexsoft.restlibrary.util.ValidatorUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {
    private final IBookDAO bookDAO;
    private final IAuthorDAO authorDAO;

    @Autowired
    public BookService(IBookDAO bookDAO, IAuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
    }

    @Override
    public Long uploadFile(MultipartFile file) throws ServiceException {
        Set<Book> books;
        try (InputStream in = file.getInputStream()) {
            IBookLoader bookLoader;
            FileExtension extension = FileExtension.forString(FilenameUtils.getExtension(file.getOriginalFilename()));
            switch (extension) {
                case CSV:
                    bookLoader = new CSVBookLoader(new BookParser(), new CSVReader(in));
                    break;
                case XLS:
                    bookLoader = new ExcelBookLoader(new BookParser(), new ExcelReader(new HSSFWorkbook(in)));
                    break;
                case XLSX:
                    bookLoader = new ExcelBookLoader(new BookParser(), new ExcelReader(new XSSFWorkbook(in)));
                    break;
                default:
                    throw new ServiceException("Can't load ." + extension.getExtension() + " file.");
            }
            books = bookLoader.loadBooks();
        } catch (IOException e) {
            throw new ServiceException("Failed or interrupted I/O operations when Excel file was loaded. Filename: " + file.getOriginalFilename() + ".", e);
        }
        if (books.isEmpty()) {
            return 0L;
        }
        mergeAuthorsWithDB(books);
        Long booksLoaded = books.parallelStream()
                .reduce(0L, (count, book) -> count + book.getBookAccounting().getTotal(), Long::sum);
        mergeBooksWithDB(books);
        saveOrUpdateAll(books);
        return booksLoaded;
    }

    @Override
    public List<BookDTO> getAllBooksDTO(BookFilter bookFilter) {
        List<Book> bookList;
        if (bookFilter == null) {
            bookList = getAll();
        } else {
            bookList = bookDAO.findAllFilterCriteria(bookFilter);
        }
        return bookList.parallelStream()
                .map(DTOUtils::convertBookToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveOrUpdateAll(Set<Book> books) throws ServiceException {
        if (books == null) {
            throw new ServiceException("Books list must not be null.");
        }
        for (Book b : books) {
            bookDAO.saveOrUpdate(b);
        }
    }

    @Override
    public InputStreamResource convertToCSV(List<BookDTO> bookDTOList) throws ServiceException {
        try {
            ICSVWriter writer = new CSVWriter();
            InputStream in = writer.toInputStream(CSVBookLoader.DEFAULT_CSV_HEADER.toArray(new String[]{}), convertFrom(bookDTOList));
            return new InputStreamResource(in);
        } catch (IOException e) {
            throw new ServiceException("Failed or interrupted I/O operations while creating CSV InputStreamResource.", e);
        }
    }

    private Collection<Collection<String>> convertFrom(List<BookDTO> bookDTOList) {
        return bookDTOList.stream()
                .map(this::convertToStringList)
                .collect(Collectors.toList());
    }

    private List<String> convertToStringList(BookDTO bookDTO) {
        StringJoiner stringJoiner = new StringJoiner(CSVBookLoader.AUTHORS_DIVIDER);
        bookDTO.getAuthors().forEach(author -> stringJoiner.add(author.toString()));
        return Arrays.asList(bookDTO.getName(), bookDTO.getGenre().name(), bookDTO.getReleaseDate(), stringJoiner.toString());
    }

    private void mergeAuthorsWithDB(Set<Book> books) {
        Set<String> authorNameSet = new HashSet<>();
        Set<String> authorSurnameSet = new HashSet<>();
        books.stream()
                .flatMap(book -> book.getAuthors().stream())
                .distinct()
                .forEach(author -> {
                    authorNameSet.add(author.getName());
                    authorSurnameSet.add(author.getSurname());
                });
        List<Author> authorsFromDb = authorDAO.findAllByNamesAndSurnamesNative(authorNameSet, authorSurnameSet);
        books.stream()
                .flatMap(book -> book.getAuthors().stream())
                .forEach(author -> mergeAuthorWithDB(author, authorsFromDb));
    }

    private void mergeBooksWithDB(Set<Book> books) {
        Set<String> bookNameSet = new HashSet<>();
        Set<Long> authorIdSet = new HashSet<>();
        books.forEach(book -> {
            bookNameSet.add(book.getName());
            book.getAuthors().forEach(author -> authorIdSet.add(author.getId()));
        });
        List<Book> booksFromDb = bookDAO.findAllByNamesAndAuthorIdSetNative(bookNameSet, authorIdSet);
        books.forEach(newBook -> mergeBookWithDB(newBook, booksFromDb));
    }

    private void mergeAuthorWithDB(Author authorFromFile, List<Author> authorsFromDB) {
        int index = authorsFromDB.indexOf(authorFromFile);
        if (index == -1) {
            throw new IllegalArgumentException("File contains author that is not in the database. Author: " + authorFromFile + ".");
        }
        authorFromFile.setId(authorsFromDB.get(index).getId());
    }

    private void mergeBookWithDB(Book book, List<Book> booksFromDB) {
        int index = booksFromDB.indexOf(book);
        if (index != -1) {
            Book bookFromDb = booksFromDB.get(index);
            Long totalFromDb = bookFromDb.getBookAccounting().getTotal();
            Long availableFromDb = bookFromDb.getBookAccounting().getAvailable();
            Long totalFromFile = book.getBookAccounting().getTotal();
            Long availableFromFile = book.getBookAccounting().getAvailable();
            book.setId(bookFromDb.getId());
            book.getBookAccounting().setTotal(totalFromDb + totalFromFile);
            book.getBookAccounting().setAvailable(availableFromDb + availableFromFile);
            book.getBookAccounting().setId(bookFromDb.getBookAccounting().getId());
        }
    }

    @Override
    public Book getOne(Long id) throws ServiceException {
        if (!ValidatorUtils.isValidId(id)) {
            throw new ServiceException("Illegal book_id value. book_id = " + id + ".");
        }
        return bookDAO.getOne(id).orElseThrow(() ->
                new ServiceException("Book does not exists."));
    }

    @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
    }

    @Override
    public Book create(Book entity) throws ServiceException {
        return null;
    }

    @Override
    public Book update(Book entity) throws ServiceException {
        return null;
    }

    @Override
    public Book saveOrUpdate(Book entity) throws ServiceException {
        return null;
    }

    @Override
    public void delete(Book entity) throws ServiceException {

    }

    @Override
    public void delete(Long id) throws ServiceException {

    }
}

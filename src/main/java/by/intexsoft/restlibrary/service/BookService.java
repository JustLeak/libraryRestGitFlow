package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.dao.api.IAuthorDAO;
import by.intexsoft.restlibrary.model.dao.api.IBookDAO;
import by.intexsoft.restlibrary.model.dto.BookDTO;
import by.intexsoft.restlibrary.model.enumeration.FileExtension;
import by.intexsoft.restlibrary.service.api.IBookService;
import by.intexsoft.restlibrary.service.loader.XLSBookLoader;
import by.intexsoft.restlibrary.service.loader.XLSXBookLoader;
import by.intexsoft.restlibrary.service.loader.api.IBookLoader;
import by.intexsoft.restlibrary.util.DTOUtils;
import by.intexsoft.restlibrary.util.FileResolver;
import by.intexsoft.restlibrary.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public Long uploadExcelFile(MultipartFile file) throws ServiceException {
        IBookLoader bookLoader;
        FileExtension fileExtension = FileResolver.resolveExtension(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            switch (fileExtension) {
                case XLS:
                    bookLoader = new XLSBookLoader(inputStream);
                    break;
                case XLSX:
                    bookLoader = new XLSXBookLoader(inputStream);
                    break;
                default:
                    throw new ServiceException("Input file must have .xls or .xlsx extension.");
            }
        } catch (IOException e) {
            throw new ServiceException("Failed or interrupted I/O operations when Excel file was loaded. Filename: " + file.getOriginalFilename() + ".", e);
        }
        Set<Book> books = bookLoader.loadAllBooks();
        mergeWithDatabaseAuthors(books);
        Long booksLoaded = books.parallelStream()
                .reduce(0L, (aLong, book) -> aLong + book.getBookAccounting().getTotal(), Long::sum);
        mergeWithDatabaseBooks(books);
        saveOrUpdateAll(books);
        return booksLoaded;
    }

    private void mergeWithDatabaseAuthors(Set<Book> books) {
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
                .forEach(author -> mergeAuthorToDatabase(author, authorsFromDb));
    }

    private void mergeWithDatabaseBooks(Set<Book> books) {
        if (books == null) {
            throw new IllegalArgumentException("Books must not be null.");
        }
        Set<String> bookNameSet = new HashSet<>();
        Set<Long> authorIdSet = new HashSet<>();
        books.forEach(book -> {
            bookNameSet.add(book.getName());
            book.getAuthors().forEach(author -> authorIdSet.add(author.getId()));
        });
        List<Book> booksFromDb = bookDAO.findAllByNamesAndAuthorIdSetNative(bookNameSet, authorIdSet);
        books.forEach(newBook -> mergeBookToDatabase(newBook, booksFromDb));
    }

    @Override
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
    public Book getOne(Long id) throws ServiceException {
        if (!ValidatorUtils.isValidId(id)) {
            throw new ServiceException("Illegal book_id value. book_id  = " + id + ".");
        }
        return bookDAO.getOne(id).orElseThrow(() ->
                new ServiceException("Book does not exists."));
    }

    @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
    }

    @Override
    public List<BookDTO> getAllBooksDTO() {
        return getAll().stream()
                .map(DTOUtils::convertBookToDTO)
                .collect(Collectors.toList());
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

    private void mergeAuthorToDatabase(Author authorFromFile, List<Author> authorsFromDB) {
        int index = authorsFromDB.indexOf(authorFromFile);
        if (index == -1) {
            throw new IllegalArgumentException("File contains author that is not in the database. Author: " + authorFromFile + ".");
        }
        authorFromFile.setId(authorsFromDB.get(index).getId());
    }

    private void mergeBookToDatabase(Book book, List<Book> booksFromDB) {
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
}

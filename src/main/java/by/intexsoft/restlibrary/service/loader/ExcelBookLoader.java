package by.intexsoft.restlibrary.service.loader;

import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.BookAccounting;
import by.intexsoft.restlibrary.service.loader.api.IBookLoader;
import by.intexsoft.restlibrary.service.loader.buffer.BookBuffer;
import by.intexsoft.restlibrary.service.parser.BookParser;
import by.intexsoft.restlibrary.service.parser.api.IBookParser;
import by.intexsoft.restlibrary.service.reader.api.IExcelReader;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ExcelBookLoader implements IBookLoader {
    public final static List<String> DEFAULT_EXCEL_HEADER = Arrays.asList("name", "description", "release", "author");
    protected IExcelReader excelReader;
    private IBookParser bookParser;

    protected ExcelBookLoader() {
        bookParser = new BookParser();
    }

    private static BookAccounting mergeBookAccounting(BookAccounting oldBookAccounting, BookAccounting newBookAccounting) {
        Book oldBook = oldBookAccounting.getBook();
        Book newBook = newBookAccounting.getBook();
        if (!isValidBooksToMerge(oldBook, newBook)) {
            throw new IllegalArgumentException("Books with the same name and author must have the same genre and release date.");
        }
        Long newCount = oldBookAccounting.getTotal() + 1;
        oldBookAccounting.setTotal(newCount);
        oldBookAccounting.setAvailable(newCount);
        return oldBookAccounting;
    }

    private static boolean isValidBooksToMerge(Book oldBook, Book newBook) {
        if (!oldBook.getGenre().equals(newBook.getGenre())) {
            return false;
        }
        return oldBook.getReleaseDate() != null ? !oldBook.getReleaseDate().equals(newBook.getReleaseDate()) : newBook.getReleaseDate() == null;
    }

    @Override
    public Set<Book> loadAllBooks() {
        Map<Integer, List<String>> intListMap = excelReader.readSheet(0);
        if (isValidExcelHeader(intListMap.get(0))) {
            intListMap.remove(0);
        } else {
            throw new IllegalArgumentException("Illegal excel file header format. Header: " + intListMap.get(0) + ".");
        }
        return convertToResultBookSet(intListMap.values());
    }

    private Set<Book> convertToResultBookSet(Collection<List<String>> stringLists) {
        Map<Book, BookAccounting> bookCountMap = new HashMap<>();
        for (List<String> stringList : stringLists) {
            Book book = parseFrom(stringList);
            bookCountMap.merge(book, book.getBookAccounting(), ExcelBookLoader::mergeBookAccounting);
        }
        return bookCountMap.keySet();
    }

    private Book parseFrom(List<String> stringList) {
        BookBuffer buffer = convertFrom(stringList);
        Book book = bookParser.parseFrom(buffer);
        book.setBookAccounting(new BookAccounting(book));
        return book;
    }

    private BookBuffer convertFrom(List<String> stringList) {
        if (stringList.size() == DEFAULT_EXCEL_HEADER.size()) {
            return new BookBuffer(stringList.get(0), stringList.get(1), stringList.get(2), stringList.get(3));
        } else if (stringList.size() == DEFAULT_EXCEL_HEADER.size() - 1) {
            return new BookBuffer(stringList.get(0), stringList.get(1), null, stringList.get(2));
        } else {
            throw new IllegalArgumentException("Illegal input list size. Size = " + stringList.size() + ".");
        }
    }

    private boolean isValidExcelHeader(List<String> header) {
        if (header == null || header.size() != DEFAULT_EXCEL_HEADER.size()) {
            return false;
        }
        List<String> headerLowerCase = header.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return headerLowerCase.equals(DEFAULT_EXCEL_HEADER);
    }
}

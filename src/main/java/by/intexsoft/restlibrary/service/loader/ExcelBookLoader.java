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

    @Override
    public Set<Book> loadAllBooks() {
        Map<Integer, List<String>> intListMap = excelReader.readSheet(0);
        if (isValidExcelHeader(intListMap.get(0))) {
            intListMap.remove(0);
        } else {
            throw new IllegalArgumentException("Illegal excel file header format. Header: " + intListMap.get(0) + ".");
        }
        return parseAndCountBooks(intListMap.values());
    }

    private Set<Book> parseAndCountBooks(Collection<List<String>> stringLists) {
        Set<Book> result = new HashSet<>();
        stringLists.forEach(stringList -> {
            BookBuffer buffer = convertToBufferList(stringList);
            Book newBook = bookParser.parseFrom(buffer);
            boolean isExistsBook = result.stream().anyMatch(existingBook -> {
                if (existingBook.equals(newBook)) {
                    if (!existingBook.getGenre().equals(newBook.getGenre())) {
                        throw new IllegalArgumentException("Books with the same name and author must have the same genre.");
                    }
                    if (existingBook.getReleaseDate() != null ? existingBook.getReleaseDate().equals(newBook.getReleaseDate()) : newBook.getReleaseDate() != null) {
                        throw new IllegalArgumentException("Books with the same name and author must have the same release date.");
                    }
                    BookAccounting bookAccounting = existingBook.getBookAccounting();
                    bookAccounting.setAvailable(bookAccounting.getAvailable() + 1);
                    bookAccounting.setTotal(bookAccounting.getTotal() + 1);
                    return true;
                }
                return false;
            });
            if (!isExistsBook) {
                newBook.setBookAccounting(new BookAccounting(newBook));
                result.add(newBook);
            }
        });
        return result;
    }

    private BookBuffer convertToBufferList(List<String> stringList) {
        if (stringList.size() == 4) {
            return new BookBuffer(stringList.get(0), stringList.get(1), stringList.get(2), stringList.get(3));
        } else if (stringList.size() == 3) {
            return new BookBuffer(stringList.get(0), stringList.get(1), null, stringList.get(2));
        } else {
            throw new IllegalArgumentException("Illegal input list size. Size = " + stringList.size() + ".");
        }
    }

    private boolean isValidExcelHeader(List<String> header) {
        if (header == null || header.size() != 4) {
            return false;
        }
        List<String> headerLowerCase = header.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return headerLowerCase.equals(DEFAULT_EXCEL_HEADER);
    }
}

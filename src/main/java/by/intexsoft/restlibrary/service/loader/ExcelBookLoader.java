package by.intexsoft.restlibrary.service.loader;

import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.BookAccounting;
import by.intexsoft.restlibrary.service.loader.api.IBookLoader;
import by.intexsoft.restlibrary.service.parser.api.IBookParser;
import by.intexsoft.restlibrary.service.reader.api.IExcelReader;
import by.intexsoft.restlibrary.util.DateConverter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ExcelBookLoader extends BookLoader implements IBookLoader {
    public static final List<String> DEFAULT_EXCEL_HEADER = Arrays.asList("name", "description", "release", "author");
    public static final String AUTHORS_DIVIDER = ", ";
    private final IExcelReader reader;

    public ExcelBookLoader(IBookParser bookParser, IExcelReader reader) {
        super(bookParser);
        this.reader = reader;
    }

    @Override
    protected Collection<List<String>> readBooks() {
        Map<Integer, List<String>> readData = reader.readSheet(0);
        if (!DEFAULT_EXCEL_HEADER.equals(readData.get(0))) {
            throw new IllegalArgumentException("Illegal excel file header format. Header: " + readData.get(0) + ".");
        }
        readData.remove(0);
        return readData.values();
    }

    @Override
    protected Book create(List<String> strList) {
        Book book = new Book();
        book.setName(bookParser.parseName(strList.get(0)));
        book.setGenre(bookParser.parseGenreFromDescription(strList.get(1)));
        if (strList.get(2) != null && !strList.get(2).equals("")) {
            book.setReleaseDate(DateConverter.convertFrom(strList.get(2)));
        }
        book.setAuthors(bookParser.parseAuthors(strList.get(3), AUTHORS_DIVIDER));
        book.setBookAccounting(new BookAccounting(book));
        return book;
    }
}

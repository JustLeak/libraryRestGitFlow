package by.intexsoft.restlibrary.service.loader;

import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.BookAccounting;
import by.intexsoft.restlibrary.model.enumeration.Genre;
import by.intexsoft.restlibrary.service.loader.api.IBookLoader;
import by.intexsoft.restlibrary.service.parser.api.IBookParser;
import by.intexsoft.restlibrary.service.reader.api.ICSVReader;
import by.intexsoft.restlibrary.util.DateConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CSVBookLoader extends BookLoader implements IBookLoader {
    public static final List<String> DEFAULT_CSV_HEADER = Arrays.asList("name", "genre", "release", "author");
    public static final String AUTHORS_DIVIDER = "-";
    private final ICSVReader reader;

    public CSVBookLoader(IBookParser bookParser, ICSVReader reader) {
        super(bookParser);
        this.reader = reader;
    }

    @Override
    protected Collection<List<String>> readBooks() throws IOException {
        return reader.readAllRecordsWithHeader(DEFAULT_CSV_HEADER).values();
    }

    @Override
    protected Book create(List<String> strList) {
        Book book = new Book();
        book.setName(bookParser.parseName(strList.get(0)));
        book.setGenre(Genre.forString(strList.get(1)));
        if (strList.get(2) != null && !strList.get(2).equals("")) {
            book.setReleaseDate(DateConverter.convertFrom(strList.get(2)));
        }
        book.setAuthors(bookParser.parseAuthors(strList.get(3), AUTHORS_DIVIDER));
        book.setBookAccounting(new BookAccounting(book));
        return book;
    }
}

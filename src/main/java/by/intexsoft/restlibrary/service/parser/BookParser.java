package by.intexsoft.restlibrary.service.parser;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.enumeration.Genre;
import by.intexsoft.restlibrary.service.loader.buffer.BookBuffer;
import by.intexsoft.restlibrary.service.parser.api.IBookParser;
import by.intexsoft.restlibrary.util.DateConverter;
import by.intexsoft.restlibrary.util.ValidatorUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookParser implements IBookParser {
    public static final String AUTHORS_DIVIDER = ", ";
    public static final String NAMES_DIVIDER = " ";

    @Override
    public Genre parseFromDescription(String desc) {
        if (desc == null) {
            throw new IllegalArgumentException("Description must be not null.");
        }
        if (desc.length() > 500) {
            throw new IllegalArgumentException("Description length must be lesser than 500.");
        }
        String descLowerCase = desc.toLowerCase();
        return Stream.of(Genre.values())
                .map(genre -> new Pair<>(genre, countGenreIn(descLowerCase, genre)))
                .max((o1, o2) -> o1.getValue() > o2.getValue() ? 1 : -1)
                .orElseThrow(() -> new IllegalArgumentException("Can't resolve genre from description."))
                .getKey();
    }

    @Override
    public Set<Author> parseAuthors(String authors, String divider) {
        if (authors == null) {
            throw new IllegalArgumentException("Authors must be not null.");
        }
        if (divider == null) {
            throw new IllegalArgumentException("Divider must be not null.");
        }
        List<String> authorList = Arrays.asList(authors.split(divider));
        Set<Author> authorSet = authorList.parallelStream()
                .map(this::parseFrom)
                .collect(Collectors.toSet());
        if (authorList.size() != authorSet.size()) {
            throw new IllegalArgumentException("Authors must be unique.");
        }
        return authorSet;
    }

    @Override
    public Book parseFrom(BookBuffer bookBuffer) {
        if (bookBuffer == null) {
            throw new IllegalArgumentException("BookBuffer must be not null.");
        }
        Book book = new Book();
        book.setName(parseName(bookBuffer.getName()));
        book.setGenre(parseFromDescription(bookBuffer.getDescription()));
        book.setAuthors(parseAuthors(bookBuffer.getAuthors(), AUTHORS_DIVIDER));
        if (bookBuffer.getDate() != null && !bookBuffer.getDate().equals("")) {
            book.setReleaseDate(DateConverter.convertFrom(bookBuffer.getDate()));
        }
        return book;
    }

    @Override
    public Author parseFrom(String str) {
        String[] nameSurname = str.split(NAMES_DIVIDER);
        if (nameSurname.length != 2) {
            throw new IllegalArgumentException("Can't resolve author. String: " + str + ".");
        }
        String name = nameSurname[0].trim();
        String surname = nameSurname[1].trim();
        if (!ValidatorUtils.isValidName(name) || !ValidatorUtils.isValidName(surname)) {
            throw new IllegalArgumentException("Illegal author name or surname. (" + name + " " + surname + ")");
        }
        return new Author(name, surname);
    }

    @Override
    public String parseName(String name) {
        if (!ValidatorUtils.isValidBookName(name)) {
            throw new IllegalArgumentException("Illegal book name. Name: " + name + ".");
        }
        return name.trim();
    }

    private Integer countGenreIn(String str, Genre genre) {
        String genreName = genre.name().toLowerCase();
        return StringUtils.countOccurrencesOf(str, genreName);
    }
}

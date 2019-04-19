package by.intexsoft.restlibrary.service.parser;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.enumeration.Genre;
import by.intexsoft.restlibrary.service.loader.buffer.BookBuffer;
import by.intexsoft.restlibrary.service.parser.api.IBookParser;
import by.intexsoft.restlibrary.util.ValidatorUtils;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class BookParser implements IBookParser {
    public static final String AUTHORS_DIVIDER = ", ";
    public static final String NAMES_DIVIDER = " ";
    public static final String DATE_FORMAT = "yyyy-[m]m-[d]d";

    @Override
    public Genre parseGenreFromDescription(String desc) {
        if (desc == null) {
            throw new IllegalArgumentException("Description must be not null.");
        }
        if (desc.length() > 500) {
            throw new IllegalArgumentException("Description length must be lesser than 500.");
        }
        Map<Genre, Integer> genreCountMap = new HashMap<>();
        String descLowerCase = desc.toLowerCase();
        Arrays.stream(Genre.values())
                .forEach(genre -> {
                    int matchesCount = StringUtils.countOccurrencesOf(descLowerCase, genre.name().toLowerCase());
                    if (matchesCount > 0) {
                        genreCountMap.put(genre, matchesCount);
                    }
                });
        return genreCountMap.entrySet().stream()
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
        Set<Author> authorSet = authorList.stream()
                .map(s -> {
                    String[] nameSurname = s.split(NAMES_DIVIDER);
                    if (nameSurname.length == 2 && ValidatorUtils.isValidName(nameSurname[0]) && ValidatorUtils.isValidName(nameSurname[1])) {
                        return new Author(nameSurname[0], nameSurname[1]);
                    } else {
                        throw new IllegalArgumentException("Can't resolve author.");
                    }
                })
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
        String name = bookBuffer.getName();
        if (!ValidatorUtils.isValidBookName(bookBuffer.getName())) {
            throw new IllegalArgumentException("Illegal book name. Name: " + name + ".");
        }
        name = name.trim();
        Genre genre = parseGenreFromDescription(bookBuffer.getDescription());
        Date release = null;
        if (bookBuffer.getDate() != null) {
            try {
                release = Date.valueOf(bookBuffer.getDate());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Illegal book release date value. Required date format: " + DATE_FORMAT + ".");
            }
        }
        Set<Author> authors = parseAuthors(bookBuffer.getAuthors(), AUTHORS_DIVIDER);
        return new Book(name, genre, release, authors);
    }
}

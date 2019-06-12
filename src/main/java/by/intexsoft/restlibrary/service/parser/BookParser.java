package by.intexsoft.restlibrary.service.parser;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.enumeration.Genre;
import by.intexsoft.restlibrary.service.parser.api.IBookParser;
import by.intexsoft.restlibrary.util.ValidatorUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookParser implements IBookParser {
    public static final String NAMES_DIVIDER = " ";

    @Override
    public Genre parseGenreFromDescription(String desc) {
        if (desc == null) {
            throw new IllegalArgumentException("Description must be not null.");
        }
        if (desc.length() > 500) {
            throw new IllegalArgumentException("Description length must be lesser than 500.");
        }
        String descLowerCase = desc.toLowerCase();
        Pair<Genre, Integer> genreCount = Stream.of(Genre.values())
                .map(genre -> new Pair<>(genre, countGenreIn(descLowerCase, genre)))
                .max(Comparator.comparingInt(Pair::getSecond))
                .get();
        if (genreCount.getSecond() == 0) {
            throw new IllegalArgumentException("Can't parse genre from description. Description: " + desc + ".");
        }
        return genreCount.getFirst();
    }

    @Override
    public Set<Author> parseAuthors(String authors, String authorsDivider) {
        if (authors == null) {
            throw new IllegalArgumentException("Authors must be not null.");
        }
        if (!isValidAuthorsDivider(authorsDivider)) {
            throw new IllegalArgumentException("Illegal authors divider: " + authorsDivider + ".");
        }
        List<String> authorList = Arrays.asList(authors.split(authorsDivider));
        Set<Author> authorSet = authorList.parallelStream()
                .map(this::parseAuthor)
                .collect(Collectors.toSet());
        if (authorList.size() != authorSet.size()) {
            throw new IllegalArgumentException("Authors must be unique.");
        }
        return authorSet;
    }

    @Override
    public Author parseAuthor(String author) {
        String[] nameSurname = author.split(NAMES_DIVIDER);
        if (nameSurname.length != 2) {
            throw new IllegalArgumentException("Can't resolve author. String: " + author + ".");
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
        String trimName = name.trim();
        if (!ValidatorUtils.isValidBookName(trimName)) {
            throw new IllegalArgumentException("Illegal book name. Name: " + name + ".");
        }
        return trimName;
    }

    private Integer countGenreIn(String str, Genre genre) {
        String genreName = genre.name().toLowerCase();
        return StringUtils.countOccurrencesOf(str, genreName);
    }

    private boolean isValidAuthorsDivider(String divider) {
        return divider != null && !divider.equals("");
    }
}

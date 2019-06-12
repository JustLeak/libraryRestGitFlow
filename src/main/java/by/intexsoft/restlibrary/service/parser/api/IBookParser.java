package by.intexsoft.restlibrary.service.parser.api;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.enumeration.Genre;

import java.util.Set;

public interface IBookParser {

    Genre parseGenreFromDescription(String desc);

    Set<Author> parseAuthors(String authors, String divider);

    Author parseAuthor(String author);

    String parseName(String name);
}

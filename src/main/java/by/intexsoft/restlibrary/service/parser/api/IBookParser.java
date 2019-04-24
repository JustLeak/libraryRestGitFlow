package by.intexsoft.restlibrary.service.parser.api;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.enumeration.Genre;
import by.intexsoft.restlibrary.service.loader.buffer.BookBuffer;

import java.util.Set;

public interface IBookParser {

    Genre parseFromDescription(String desc);

    Set<Author> parseAuthors(String authors, String divider);

    Author parseFrom(String str);

    String parseName(String name);

    Book parseFrom(BookBuffer bookBuffer);
}

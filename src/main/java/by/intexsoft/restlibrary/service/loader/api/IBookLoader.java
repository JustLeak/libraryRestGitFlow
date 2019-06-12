package by.intexsoft.restlibrary.service.loader.api;

import by.intexsoft.restlibrary.model.Book;

import java.io.IOException;
import java.util.Set;

public interface IBookLoader {

    Set<Book> loadBooks() throws IOException;
}

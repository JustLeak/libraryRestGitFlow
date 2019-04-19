package by.intexsoft.restlibrary.service.loader.api;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Book;

import java.util.Set;

public interface IBookLoader {

    Set<Book> loadAllBooks() throws ServiceException;
}

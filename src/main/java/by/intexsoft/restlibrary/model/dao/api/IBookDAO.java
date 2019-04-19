package by.intexsoft.restlibrary.model.dao.api;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IBookDAO extends ICrudDAO<Book, Long> {

    Optional<Book> findByNameAndAuthors(String name, Set<Author> authors);

    Optional<Book> findByNameAndAuthorsNative(String name, Set<Author> authors);

    List<Book> findAllByNamesAndAuthorIdSetNative(Set<String> names, Set<Long> authorIdSet);
}

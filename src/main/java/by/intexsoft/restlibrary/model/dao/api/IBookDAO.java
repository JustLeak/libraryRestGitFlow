package by.intexsoft.restlibrary.model.dao.api;

import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.filter.BookFilter;

import java.util.List;
import java.util.Set;

public interface IBookDAO extends ICrudDAO<Book, Long> {

    List<Book> findAllByNamesAndAuthorIdSetNative(Set<String> names, Set<Long> authorIdSet);

    List<Book> findAllFilterCriteria(BookFilter filter);
}

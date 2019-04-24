package by.intexsoft.restlibrary.model.dao.api;

import by.intexsoft.restlibrary.model.Author;

import java.util.List;
import java.util.Set;

public interface IAuthorDAO extends ICrudDAO<Author, Long> {

    List<Author> findAllByNamesAndSurnamesNative(Set<String> names, Set<String> surnames);
}

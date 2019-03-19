package by.intexsoft.restlibrary.model.dao.api;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ICrudDAO<T, K extends Serializable> {

    Optional<T> getOne(K id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    T saveOrUpdate(T entity);

    void delete(T entity);

    void delete(K id);
}

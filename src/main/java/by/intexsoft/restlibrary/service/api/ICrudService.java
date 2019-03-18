package by.intexsoft.restlibrary.service.api;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ICrudService<T, K extends Serializable> {

    Optional<T> getOne(K id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void delete(K id);
}

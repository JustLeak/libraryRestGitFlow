package by.intexsoft.restlibrary.repository.api;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ICrudRepo<T, K extends Serializable> {

    Optional<T> getOne(K id);

    List<T> getAll();

    @Transactional
    T create(T entity);

    @Transactional
    T update(T entity);

    @Transactional
    T saveOrUpdate(T entity);

    @Transactional
    void delete(T entity);

    @Transactional
    void delete(K id);
}

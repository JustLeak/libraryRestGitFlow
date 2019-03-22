package by.intexsoft.restlibrary.service.api;

import by.intexsoft.restlibrary.exception.ServiceException;

import java.io.Serializable;
import java.util.List;

public interface ICrudService<T, K extends Serializable> {

    T getOne(K id) throws ServiceException;

    List<T> getAll();

    T create(T entity) throws ServiceException;

    T update(T entity) throws ServiceException;

    T saveOrUpdate(T entity) throws ServiceException;

    void delete(T entity) throws ServiceException;

    void delete(K id) throws ServiceException;
}

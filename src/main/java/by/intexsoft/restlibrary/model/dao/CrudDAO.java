package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.dao.api.ICrudDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public abstract class CrudDAO<T, K extends Serializable> implements ICrudDAO<T, K> {
    private Class<T> clazz;

    @Autowired
    private SessionFactory sessionFactory;

    public CrudDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Optional<T> getOne(K id) {
        String hql = "FROM " + clazz.getName() + " c WHERE c.id = :id";
        Session session = openSession();
        Query<T> query = session.createQuery(hql, clazz);
        query.setParameter("id", id);
        Optional<T> resultOptional = query.uniqueResultOptional();
        session.close();
        return resultOptional;
    }

    @Override
    public List<T> getAll() {
        String hql = "FROM " + clazz.getName();
        Session session = openSession();
        List<T> resultList = session.createQuery(hql, clazz).getResultList();
        session.close();
        return resultList;
    }

    @Override
    @Transactional
    public T create(T entity) {
        getCurrentSession().persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public T saveOrUpdate(T entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(T entity) {
        getCurrentSession().update(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    @Transactional
    public void delete(K id) {
        getOne(id).ifPresent(this::delete);
    }
}

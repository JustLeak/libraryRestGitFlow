package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.dao.api.IAuthorDAO;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class AuthorDAO extends CrudDAO<Author, Long> implements IAuthorDAO {

    public AuthorDAO() {
        super(Author.class);
    }

    @Override
    public List<Author> findAllByNamesAndSurnamesNative(Set<String> names, Set<String> surnames) {
        String sql = "SELECT * FROM Author a " +
                "WHERE a.name IN (:nameSet) " +
                "AND a.surname IN (:surnameSet)";
        Session session = openSession();
        List<Author> authors = session.createNativeQuery(sql, Author.class)
                .setParameterList("nameSet", names)
                .setParameterList("surnameSet", surnames)
                .getResultList();
        session.close();
        return authors;
    }
}

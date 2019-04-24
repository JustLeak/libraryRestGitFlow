package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.dao.api.IBookDAO;
import by.intexsoft.restlibrary.model.filter.BookFilter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class BookDAO extends CrudDAO<Book, Long> implements IBookDAO {

    public BookDAO() {
        super(Book.class);
    }

    @Override
    public List<Book> findAllByNamesAndAuthorIdSetNative(Set<String> names, Set<Long> authorIdSet) {
        String sql = "SELECT * " +
                "FROM book b " +
                "INNER JOIN books_authors ba ON ba.book_book_id = book_id " +
                "WHERE ba.author_author_id IN (:authorIdSet) " +
                "AND b.name IN (:nameSet)";
        Session session = openSession();
        List<Book> books = session.createNativeQuery(sql, Book.class)
                .setParameterList("authorIdSet", authorIdSet)
                .setParameterList("nameSet", names)
                .getResultList();
        session.close();
        return books;
    }

    @Override
    public List<Book> findAllFilterCriteria(BookFilter filter) {
        Session session = openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> booksRoot = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getName() != null) {
            predicates.add(cb.equal(booksRoot.get("name"), filter.getName()));
        }
        if (filter.getGenre() != null) {
            predicates.add(cb.equal(booksRoot.get("genre"), filter.getGenre()));
        }
        if (filter.getFrom() != null) {
            predicates.add(cb.greaterThan(booksRoot.get("releaseDate"), filter.getFrom()));
        }
        if (filter.getTo() != null) {
            predicates.add(cb.lessThan(booksRoot.get("releaseDate"), filter.getTo()));
        }
        cq.select(booksRoot)
                .where(predicates.toArray(new Predicate[]{}));
        List<Book> result = session.createQuery(cq).getResultList();
        session.close();
        return result;
    }
}

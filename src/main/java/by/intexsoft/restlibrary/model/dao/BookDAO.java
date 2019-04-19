package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.dao.api.IBookDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookDAO extends CrudDAO<Book, Long> implements IBookDAO {

    public BookDAO() {
        super(Book.class);
    }

    @Override
    public Optional<Book> findByNameAndAuthorsNative(String name, Set<Author> authors) {
        /*String sql = "SELECT * " +
                "FROM book b " +
                "INNER JOIN books_authors ba ON ba.book_book_id = book_id " +
                "WHERE ba.author_author_id IN (:authorIdSet) " +
                "AND b.name = :name" +
                "GROUP BY ba.book_book_id " +
                "HAVING COUNT(ba.book_book_id) = :authorsCount";*/
        String sql = "SELECT * FROM Book b " +
                "INNER JOIN books_authors ba ON b.book_id = ba.book_book_id " +
                "INNER JOIN Author a ON ba.author_author_id = a.author_id " +
                "WHERE b.name = :name";
        Session session = openSession();
        List<Book> books = session.createNativeQuery(sql, Book.class)
                .setParameter("name", name)
                .getResultList();
        session.close();
        for (Book book : books) {
            if (book.getAuthors().equals(authors)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
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
    public Optional<Book> findByNameAndAuthors(String name, Set<Author> authors) {
        String hql = "FROM Book b JOIN FETCH b.authors a WHERE a IN (:authors) AND b.name =:name";
        Session session = openSession();
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("name", name);
        query.setParameterList("authors", authors);
        Optional<Book> result = query.uniqueResultOptional();
        session.close();
        if (result.isPresent() && result.get().getAuthors().size() == authors.size()) {
            return result;
        } else {
            return Optional.empty();
        }
    }
}

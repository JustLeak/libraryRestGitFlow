package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dao.api.ICardDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class CardDAO extends CrudDAO<LibraryCard, Long> implements ICardDAO {

    public CardDAO() {
        super(LibraryCard.class);
    }

    @Override
    public Optional<LibraryCard> findByUId(Long id) {
        String hql = "FROM LibraryCard lc WHERE lc.client.id = :id";
        Session session = openSession();
        Query<LibraryCard> query = session.createQuery(hql, LibraryCard.class);
        query.setParameter("id", id);
        Optional<LibraryCard> resultOptional = query.uniqueResultOptional();
        session.close();
        return resultOptional;
    }
}

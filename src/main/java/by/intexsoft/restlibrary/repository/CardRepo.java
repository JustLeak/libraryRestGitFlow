package by.intexsoft.restlibrary.repository;

import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.repository.api.ICardRepo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class CardRepo extends CrudRepo<LibraryCard, Long> implements ICardRepo {

    public CardRepo() {
        super(LibraryCard.class);
    }

    public Optional<LibraryCard> findCardByUId(Long id) {
        String hql = "FROM LibraryCard lc WHERE lc.client.id = :id";

        Session session = openSession();
        Query<LibraryCard> query = session.createQuery(hql, LibraryCard.class);
        query.setParameter("id", id);
        Optional<LibraryCard> resultOptional = query.uniqueResultOptional();
        session.close();

        return resultOptional;
    }
}

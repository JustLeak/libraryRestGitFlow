package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dao.api.ICardDAO;
import org.springframework.stereotype.Repository;

@Repository
public class CardDAO extends CrudDAO<LibraryCard, Long> implements ICardDAO {

    public CardDAO() {
        super(LibraryCard.class);
    }
}

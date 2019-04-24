package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.BookAccounting;
import by.intexsoft.restlibrary.model.dao.api.IBookAccountingDAO;
import org.springframework.stereotype.Repository;

@Repository
public class BookAccountingDAO extends CrudDAO<BookAccounting, Long> implements IBookAccountingDAO {

    public BookAccountingDAO() {
        super(BookAccounting.class);
    }
}

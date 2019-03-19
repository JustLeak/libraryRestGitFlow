package by.intexsoft.restlibrary.model.dao;

import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.dao.api.IClientDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDAO extends CrudDAO<Client, Long> implements IClientDAO {

    public ClientDAO() {
        super(Client.class);
    }
}

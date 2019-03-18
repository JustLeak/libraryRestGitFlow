package by.intexsoft.restlibrary.repository;

import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.repository.api.IClientRepo;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepo extends CrudRepo<Client, Long> implements IClientRepo {

    public ClientRepo() {
        super(Client.class);
    }
}

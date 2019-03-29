package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.dao.api.IClientDAO;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.service.api.IClientService;
import by.intexsoft.restlibrary.service.api.ICrudService;
import by.intexsoft.restlibrary.util.DTOUtil;
import by.intexsoft.restlibrary.util.ServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService, ICrudService<Client, Long> {
    private final IClientDAO clientDAO;

    @Autowired
    public ClientService(IClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public Client getOne(Long id) throws ServiceException {
        if (!ServiceValidator.isValidId(id)) {
            throw new ServiceException("Illegal client_id value. client_id  = " + id + ".");
        }

        return clientDAO.getOne(id).orElseThrow(() ->
                new ServiceException("Client does not exists."));
    }

    @Override
    public List<Client> getAll() {
        return clientDAO.getAll();
    }

    @Override
    public Client create(Client entity) throws ServiceException {
        checkClient(entity);
        return clientDAO.create(entity);
    }

    @Override
    public Client update(Client entity) throws ServiceException {
        checkClient(entity);
        if (!ServiceValidator.isValidId(entity.getId())) {
            throw new ServiceException("Illegal client_id value. client_id  = " + entity.getId() + ".");
        }
        return clientDAO.update(entity);
    }

    @Override
    public Client saveOrUpdate(Client entity) throws ServiceException {
        checkClient(entity);
        return clientDAO.saveOrUpdate(entity);
    }

    @Override
    public void delete(Client entity) throws ServiceException {
        if (entity == null) {
            throw new ServiceException("Client entity reference is null.");
        }
        if (!ServiceValidator.isValidId(entity.getId())) {
            throw new ServiceException("Illegal client_id value. client_id  = " + entity.getId() + ".");
        }
        clientDAO.delete(entity);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        if (!ServiceValidator.isValidId(id)) {
            throw new ServiceException("Illegal client_id value. client_id  = " + id + ".");
        }
        clientDAO.delete(id);
    }

    @Override
    public List<ClientDTO> getAllClientsDTO() {
        return getAll().stream()
                .map(DTOUtil::convertClientToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO generateAndSaveClient() {
        Client client = Client.random();
        clientDAO.create(client);
        return DTOUtil.convertClientToDTO(client);
    }

    @Override
    public ClientDTO getClientDTOById(Long clientId) throws ServiceException {
        return DTOUtil.convertClientToDTO(getOne(clientId));
    }

    @Override
    public ClientDTO saveClient(Client client) throws ServiceException {
        create(client);
        return DTOUtil.convertClientToDTO(client);
    }

    private void checkClient(Client entity) throws ServiceException {
        if (entity == null) {
            throw new ServiceException("Client entity reference is null.");
        }
        if (!ServiceValidator.isValidClientName(entity.getName())) {
            throw new ServiceException("Illegal client name.");
        }
        if (!ServiceValidator.isValidClientSurname(entity.getSurname())) {
            throw new ServiceException("Illegal client surname.");
        }
    }
}

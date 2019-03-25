package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.dao.api.IClientDAO;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.service.api.IClientService;
import by.intexsoft.restlibrary.service.api.ICrudService;
import by.intexsoft.restlibrary.util.DTOUtil;
import by.intexsoft.restlibrary.util.ServiceValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService, ICrudService<Client, Long> {
    private static final Logger logger = Logger.getLogger(ClientService.class);
    private final IClientDAO clientDAO;

    @Autowired
    public ClientService(IClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public Client getOne(Long id) throws ServiceException {
        ServiceValidator.isValidIdOrThrow(id);
        return clientDAO.getOne(id).orElseThrow(() ->
                new ServiceException("Client does not exists."));
    }

    @Override
    public List<Client> getAll() {
        return clientDAO.getAll();
    }

    @Override
    public Client create(Client entity) throws ServiceException {
        ServiceValidator.isValidClientOrThrow(entity);
        return clientDAO.create(entity);
    }

    @Override
    public Client update(Client entity) throws ServiceException {
        ServiceValidator.isValidClientOrThrow(entity);
        ServiceValidator.isValidIdOrThrow(entity.getId());
        return clientDAO.update(entity);
    }

    @Override
    public Client saveOrUpdate(Client entity) throws ServiceException {
        if (entity.getId() != null)
            throw new ServiceException("Client id must be null.", new IllegalArgumentException("Client id = " + entity.getId() + "."));

        ServiceValidator.isValidClientOrThrow(entity);
        return clientDAO.saveOrUpdate(entity);
    }

    @Override
    public void delete(Client entity) throws ServiceException {
        if (entity == null)
            throw new ServiceException("Client must be not null.", new NullPointerException("Client object is null."));

        ServiceValidator.isValidIdOrThrow(entity.getId());
        clientDAO.delete(entity);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        ServiceValidator.isValidIdOrThrow(id);
        clientDAO.delete(id);
    }

    @Override
    public List<ClientDTO> getAllClientsDTO() {
        try {
            return getAll().stream()
                    .map(DTOUtil::clientToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }

    @Override
    public ClientDTO generateAndSaveClient() {
        try {
            Client client = Client.random();
            clientDAO.create(client);
            return DTOUtil.clientToDTO(client);
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }

    @Override
    public ClientDTO getClientDTOById(Long clientId) throws ServiceException {
        try {
            return DTOUtil.clientToDTO(getOne(clientId));
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }

    @Override
    public ClientDTO saveClient(Client client) throws ServiceException {
        try {
            create(client);
            return DTOUtil.clientToDTO(client);
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }
}

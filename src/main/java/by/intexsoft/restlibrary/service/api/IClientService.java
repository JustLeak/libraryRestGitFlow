package by.intexsoft.restlibrary.service.api;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.dto.ClientDTO;

import java.util.List;

public interface IClientService extends ICrudService<Client, Long> {

    ClientDTO generateAndSaveClient();

    List<ClientDTO> getAllClientsDTO();

    ClientDTO getClientDTOById(Long clientId) throws ServiceException;

    ClientDTO saveClient(Client client) throws ServiceException;
}

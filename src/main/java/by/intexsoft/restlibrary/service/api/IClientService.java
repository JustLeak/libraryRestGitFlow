package by.intexsoft.restlibrary.service.api;

import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;

import java.util.List;
import java.util.Optional;

public interface IClientService extends ICrudService<Client, Long> {

    ClientDTO generateAndSaveClient();

    Optional<LibraryCardDTO> registerCardByClientId(Long clientId, LibraryCard newCard);

    List<ClientDTO> getAllClientsDTO();

    Optional<ClientDTO> getClientDTOById(Long clientId);

    Optional<ClientDTO> saveClient(Client client);
}

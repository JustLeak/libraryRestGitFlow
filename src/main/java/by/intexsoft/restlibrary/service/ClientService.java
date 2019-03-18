package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.repository.api.ICardRepo;
import by.intexsoft.restlibrary.repository.api.IClientRepo;
import by.intexsoft.restlibrary.service.api.IClientService;
import by.intexsoft.restlibrary.service.api.ICrudService;
import by.intexsoft.restlibrary.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService, ICrudService<Client, Long> {
    private final IClientRepo clientRepo;

    private final ICardRepo cardRepo;

    @Autowired
    public ClientService(IClientRepo clientRepo, ICardRepo cardRepo) {
        this.clientRepo = clientRepo;
        this.cardRepo = cardRepo;
    }

    @Override
    public ClientDTO generateAndSaveClient() {
        Client client = Client.random();
        clientRepo.create(client);

        return DTOUtil.clientToDTO(client);
    }

    @Override
    public Optional<Client> getOne(Long id) {
        return clientRepo.getOne(id);
    }

    @Override
    public List<Client> getAll() {
        return clientRepo.getAll();
    }

    @Override
    public Client create(Client entity) {
        return clientRepo.create(entity);
    }

    @Override
    public Client update(Client entity) {
        return clientRepo.update(entity);
    }

    @Override
    public void delete(Client entity) {
        clientRepo.delete(entity);
    }

    @Override
    public void delete(Long id) {
        clientRepo.delete(id);
    }

    @Override
    public Optional<LibraryCardDTO> registerCardByClientId(Long clientId, LibraryCard newCard) {
        Client client;

        try {
            client = clientRepo.getOne(clientId).orElseThrow();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("ClientService registerCardByClientId(Long id, LibraryCard newCard). " +
                    "Unable to register card to user. " + e.getMessage() + "(user with uId = " + clientId + ")");
            return Optional.empty();
        }

        return cardRepo.findCardByUId(clientId)
                .map(DTOUtil::cardToDTO)
                .or(() -> {
                    newCard.setClient(client);
                    return Optional.of(DTOUtil.cardToDTO(cardRepo.saveOrUpdate(newCard)));
                });
    }

    @Override
    public List<ClientDTO> getAllClientsDTO() {
        return getAll().stream()
                .map(DTOUtil::clientToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientDTO> getClientDTOById(Long clientId) {
        return getOne(clientId).map(DTOUtil::clientToDTO);
    }

    @Override
    public Optional<ClientDTO> saveClient(Client client) {
        clientRepo.create(client);

        return Optional.of(client).map(DTOUtil::clientToDTO);
    }
}

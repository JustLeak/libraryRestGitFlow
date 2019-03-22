package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dao.api.ICardDAO;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.service.api.ICardService;
import by.intexsoft.restlibrary.service.api.IClientService;
import by.intexsoft.restlibrary.util.DTOUtil;
import by.intexsoft.restlibrary.util.ServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService implements ICardService {

    private final ICardDAO cardDAO;

    private final IClientService clientService;

    @Autowired
    public CardService(ICardDAO cardDAO, IClientService clientService) {
        this.cardDAO = cardDAO;
        this.clientService = clientService;
    }

    @Override
    public LibraryCard getOne(Long id) throws ServiceException {
        ServiceValidator.isValidIdOrThrow(id);
        return cardDAO.getOne(id).orElseThrow(() ->
                new ServiceException("Card does not exists."));
    }

    @Override
    public List<LibraryCard> getAll() {
        return cardDAO.getAll();
    }

    @Override
    public LibraryCard create(LibraryCard entity) throws ServiceException {
        ServiceValidator.isValidCardOrThrow(entity);
        return cardDAO.create(entity);
    }

    @Override
    public LibraryCard update(LibraryCard entity) throws ServiceException {
        ServiceValidator.isValidCardOrThrow(entity);
        ServiceValidator.isValidIdOrThrow(entity.getId());
        return cardDAO.update(entity);
    }

    @Override
    public LibraryCard saveOrUpdate(LibraryCard entity) throws ServiceException {
        if (entity.getId() != null)
            throw new ServiceException("LibraryCard id must be null.", new IllegalArgumentException("LibraryCard id = " + entity.getId() + "."));

        ServiceValidator.isValidCardOrThrow(entity);
        return cardDAO.saveOrUpdate(entity);
    }

    @Override
    public void delete(LibraryCard entity) throws ServiceException {
        if (entity == null)
            throw new ServiceException("LibraryCard must be not null.", new NullPointerException("LibraryCard object is null."));

        ServiceValidator.isValidIdOrThrow(entity.getId());
        cardDAO.delete(entity);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        ServiceValidator.isValidIdOrThrow(id);
        cardDAO.delete(id);
    }

    @Override
    public LibraryCardDTO registerCardByClientId(Long clientId) throws ServiceException {
        try {
            Client client = clientService.getOne(clientId);
            if (client.getLibraryCard() != null)
                throw new ServiceException("Client already has a card.");

            LibraryCard card = new LibraryCard();
            card.setStartDate(new Date());
            card.setClient(client);
            return DTOUtil.cardToDTO(cardDAO.saveOrUpdate(card));
        } catch (Exception e) {
            //TODO логировать stacktrace
            throw e;
        }
    }

    @Override
    public List<LibraryCardDTO> getAllCardsDTO() {
        try {
            return getAll().stream()
                    .map(DTOUtil::cardToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            //TODO логировать stacktrace
            throw e;
        }
    }

    @Override
    public LibraryCardDTO getCardDTOById(Long cardId) throws ServiceException {
        try {
            return DTOUtil.cardToDTO(getOne(cardId));
        } catch (Exception e) {
            //TODO логировать stacktrace
            throw e;
        }
    }
}

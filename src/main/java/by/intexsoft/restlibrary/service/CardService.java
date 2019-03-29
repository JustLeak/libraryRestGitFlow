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
        if (!ServiceValidator.isValidId(id)) {
            throw new ServiceException("Illegal library_card_id reference value. Library_card_id = " + id + ".");
        }

        return cardDAO.getOne(id).orElseThrow(() ->
                new ServiceException("Library card does not exists."));
    }

    @Override
    public List<LibraryCard> getAll() {
        return cardDAO.getAll();
    }

    @Override
    public LibraryCard create(LibraryCard entity) throws ServiceException {
        checkLibraryCard(entity);
        return cardDAO.create(entity);
    }

    @Override
    public LibraryCard update(LibraryCard entity) throws ServiceException {
        checkLibraryCard(entity);
        if (!ServiceValidator.isValidId(entity.getId())) {
            throw new ServiceException("Illegal library_card_id reference value. Library_card_id = " + entity.getId() + ".");
        }
        return cardDAO.update(entity);
    }

    @Override
    public LibraryCard saveOrUpdate(LibraryCard entity) throws ServiceException {
        checkLibraryCard(entity);
        return cardDAO.saveOrUpdate(entity);
    }

    @Override
    public void delete(LibraryCard entity) throws ServiceException {
        if (entity == null) {
            throw new ServiceException("LibraryCard object is null.");
        }

        if (!ServiceValidator.isValidId(entity.getId())) {
            throw new ServiceException("Illegal library_card_id reference value. Library_card_id = " + entity.getId() + ".");
        }
        cardDAO.delete(entity);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        if (!ServiceValidator.isValidId(id)) {
            throw new ServiceException("Illegal library_card_id reference value. Library_card_id = " + id + ".");
        }
        cardDAO.delete(id);
    }

    @Override
    public LibraryCardDTO registerCardByClientId(Long clientId) throws ServiceException {
        Client client = clientService.getOne(clientId);
        if (client.getLibraryCard() != null) {
            throw new ServiceException("Client already has a card.");
        }

        LibraryCard card = new LibraryCard();
        card.setStartDate(new Date());
        card.setClient(client);
        return DTOUtil.convertCardToDTO(cardDAO.saveOrUpdate(card));
    }

    @Override
    public List<LibraryCardDTO> getAllCardsDTO() {
        return getAll().stream()
                .map(DTOUtil::convertCardToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LibraryCardDTO getCardDTOById(Long cardId) throws ServiceException {
        return DTOUtil.convertCardToDTO(getOne(cardId));
    }

    private void checkLibraryCard(LibraryCard entity) throws ServiceException {
        if (entity == null) {
            throw new ServiceException("LibraryCard entity reference is null.");
        }

        if (!ServiceValidator.isValidLibraryCardRegistrationDate(entity.getStartDate())) {
            throw new ServiceException("Illegal registration date.");
        }
    }
}

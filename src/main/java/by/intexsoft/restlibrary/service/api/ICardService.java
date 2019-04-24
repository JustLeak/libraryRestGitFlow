package by.intexsoft.restlibrary.service.api;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;

import java.util.List;

public interface ICardService extends ICrudService<LibraryCard, Long> {

    List<LibraryCardDTO> getAllCardsDTO();

    LibraryCardDTO getCardDTOById(Long cardId) throws ServiceException;

    LibraryCardDTO registerCardByClientId(Long clientId) throws ServiceException;
}

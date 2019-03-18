package by.intexsoft.restlibrary.service.api;

import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;

import java.util.List;
import java.util.Optional;

public interface ICardService extends ICrudService<LibraryCard, Long> {
    List<LibraryCardDTO> getAllCardsDTO();

    Optional<LibraryCardDTO> getCardDTOById(Long cardId);
}

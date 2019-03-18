package by.intexsoft.restlibrary.repository.api;

import by.intexsoft.restlibrary.model.LibraryCard;

import java.util.Optional;

public interface ICardRepo extends ICrudRepo<LibraryCard, Long> {

    Optional<LibraryCard> findCardByUId(Long id);
}

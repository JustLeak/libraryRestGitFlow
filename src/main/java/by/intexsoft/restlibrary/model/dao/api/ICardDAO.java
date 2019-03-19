package by.intexsoft.restlibrary.model.dao.api;

import by.intexsoft.restlibrary.model.LibraryCard;

import java.util.Optional;

public interface ICardDAO extends ICrudDAO<LibraryCard, Long> {

    Optional<LibraryCard> findCardByUId(Long id);
}

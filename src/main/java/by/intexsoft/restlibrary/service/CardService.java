package by.intexsoft.restlibrary.service;

import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.repository.api.ICardRepo;
import by.intexsoft.restlibrary.service.api.ICardService;
import by.intexsoft.restlibrary.service.api.ICrudService;
import by.intexsoft.restlibrary.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService implements ICardService, ICrudService<LibraryCard, Long> {

    private final ICardRepo cardRepo;

    @Autowired
    public CardService(ICardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public Optional<LibraryCard> getOne(Long id) {
        return cardRepo.getOne(id);
    }

    @Override
    public List<LibraryCard> getAll() {
        return cardRepo.getAll();
    }

    @Override
    public LibraryCard create(LibraryCard entity) {
        return cardRepo.create(entity);
    }

    @Override
    public LibraryCard update(LibraryCard entity) {
        return cardRepo.update(entity);
    }

    @Override
    public void delete(LibraryCard entity) {
        cardRepo.delete(entity);
    }

    @Override
    public void delete(Long id) {
        cardRepo.delete(id);
    }

    @Override
    public List<LibraryCardDTO> getAllCardsDTO() {
        return getAll().stream()
                .map(DTOUtil::cardToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LibraryCardDTO> getCardDTOById(Long cardId) {
        return getOne(cardId).map(DTOUtil::cardToDTO);
    }
}

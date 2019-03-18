package by.intexsoft.restlibrary.controllers;

import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.service.api.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final ICardService cardService;

    @Autowired
    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<LibraryCardDTO> getCards() {
        return cardService.getAllCardsDTO();
    }

    @GetMapping("/{id}")
    public LibraryCardDTO getCard(@PathVariable Long id) {
        return cardService.getCardDTOById(id).orElse(null);
    }
}

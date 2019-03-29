package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.model.response.MultiResponseList;
import by.intexsoft.restlibrary.model.response.SingleResponse;
import by.intexsoft.restlibrary.service.api.ICardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private static final Logger logger = Logger.getLogger(CardController.class);
    private final ICardService cardService;

    @Autowired
    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public MultiResponseList<LibraryCardDTO> getCards() {
        try {
            List<LibraryCardDTO> response = cardService.getAllCardsDTO();
            logger.info("Cards were shown.");
            return new MultiResponseList<>(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>("Cards were not shown. Something was wrong...");
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<LibraryCardDTO> getCard(@PathVariable Long id) {
        try {
            LibraryCardDTO response = cardService.getCardDTOById(id);
            logger.info("Card with id = " + response.getLibraryCardId() + " has been shown.");
            return new SingleResponse<>(cardService.getCardDTOById(id));
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Card has not been found. Illegal input data.");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Card has not been found. Something was wrong...");
        }
    }

    @PutMapping("/register/{id}")
    public SingleResponse<LibraryCardDTO> registerCardByClientId(@PathVariable Long id) {
        try {
            LibraryCardDTO response = cardService.registerCardByClientId(id);
            logger.info("Card with id = " + response.getLibraryCardId() + " has been registered.");
            return new SingleResponse<>(response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Card has not been registered. Illegal input data.");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Card has not been registered. Something was wrong...");
        }
    }
}

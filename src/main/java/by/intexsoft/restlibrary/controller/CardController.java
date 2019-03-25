package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.model.response.MultiResponseList;
import by.intexsoft.restlibrary.model.response.SingleResponse;
import by.intexsoft.restlibrary.service.api.ICardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        String message = "Cards were displayed. ";
        String reason = "";

        try {
            return new MultiResponseList<>(cardService.getAllCardsDTO());
        } catch (Exception e) {
            message = "Can't display cards. ";
            reason = "Something was wrong...";
            return new MultiResponseList<>(message + reason);
        } finally {
            logger.info(message + reason);
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<LibraryCardDTO> getCard(@PathVariable Long id) {
        String message = "Library card was found. ";
        String reason = "";

        try {
            return new SingleResponse<>(cardService.getCardDTOById(id));
        } catch (ServiceException e) {
            message = "Library card not found. ";
            if (e.getCause() == null)
                reason = e.getMessage();
            else reason = "Something was wrong...";

            return new SingleResponse<>(message + reason);
        } catch (Exception e) {
            message = "Library card not found. ";
            reason = "Something was wrong...";
            return new SingleResponse<>(message + reason);
        } finally {
            logger.info(message + reason);
        }
    }

    @PutMapping("/register/{id}")
    public SingleResponse<LibraryCardDTO> registerCardByClientId(@PathVariable Long id) {
        String message = "Card was registered. ";
        String reason = "";

        try {
            return new SingleResponse<>(cardService.registerCardByClientId(id));
        } catch (ServiceException e) {
            message = "Can't register library card. ";
            if (e.getCause() == null)
                reason = e.getMessage();
            else reason = "Something was wrong...";

            return new SingleResponse<>(message + reason);
        } catch (Exception e) {
            message = "Can't register library card. ";
            reason = "Something was wrong...";
            return new SingleResponse<>(message + reason);
        } finally {
            logger.info(message + reason);
        }
    }
}

package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.model.dto.MultiResponseList;
import by.intexsoft.restlibrary.model.dto.SingleResponse;
import by.intexsoft.restlibrary.service.api.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final ICardService cardService;

    @Autowired
    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public MultiResponseList<LibraryCardDTO> getCards() {
        final String message = "Can't display cards. ";
        String reason = "Something was wrong...";

        try {
            return new MultiResponseList<>(cardService.getAllCardsDTO());
        } catch (Exception e) {
            return new MultiResponseList<>(message + reason);
        } finally {
            //TODO логировать message
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<LibraryCardDTO> getCard(@PathVariable Long id) {
        final String message = "Library card not found. ";
        String reason = "Something was wrong...";

        try {
            return new SingleResponse<>(cardService.getCardDTOById(id));
        } catch (ServiceException e) {
            if (e.getCause() == null)
                reason = e.getMessage();

            return new SingleResponse<>(message + reason);
        } catch (Exception e) {
            return new SingleResponse<>(message + reason);
        } finally {
            //TODO логировать message
        }
    }

    @PutMapping("/register/{id}")
    public SingleResponse<LibraryCardDTO> registerCardByClientId(@PathVariable Long id) {
        final String message = "Can't register library card. ";
        String reason = "Something was wrong...";

        try {
            return new SingleResponse<>(cardService.registerCardByClientId(id));
        } catch (ServiceException e) {
            if (e.getCause() == null)
                reason = e.getMessage();

            return new SingleResponse<>(message + reason);
        } catch (Exception e) {
            return new SingleResponse<>(message + reason);
        } finally {
            //TODO логировать message
        }
    }
}

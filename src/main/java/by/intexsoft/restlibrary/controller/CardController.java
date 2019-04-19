package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.model.response.MultiResponseList;
import by.intexsoft.restlibrary.model.response.SingleResponse;
import by.intexsoft.restlibrary.service.api.ICardService;
import by.intexsoft.restlibrary.service.api.ILocalizationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private static final Logger logger = Logger.getLogger(CardController.class);
    private final ICardService cardService;
    private final ILocalizationService localeService;

    @Autowired
    public CardController(ICardService cardService, ILocalizationService localeService) {
        this.cardService = cardService;
        this.localeService = localeService;
    }

    @GetMapping
    public MultiResponseList<LibraryCardDTO> getCards(@RequestParam(required = false) String lang) {
        try {
            List<LibraryCardDTO> response = cardService.getAllCardsDTO();
            logger.info("Cards were shown.");
            return new MultiResponseList<>(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(localeService.getString("crwns", lang) + " " + localeService.getString("sww", lang));
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<LibraryCardDTO> getCard(@PathVariable Long id, @RequestParam(required = false) String lang) {
        try {
            LibraryCardDTO response = cardService.getCardDTOById(id);
            logger.info(String.format("Card with id = %d has been shown.", response.getLibraryCardId()));
            return new SingleResponse<>(cardService.getCardDTOById(id));
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("crhnbf", lang) + " " + localeService.getString("iid", lang));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("crhnbf", lang) + " " + localeService.getString("sww", lang));
        }
    }

    @PutMapping("/register/{id}")
    public SingleResponse<LibraryCardDTO> registerCardByClientId(@PathVariable Long id, @RequestParam(required = false) String lang) {
        try {
            LibraryCardDTO response = cardService.registerCardByClientId(id);
            logger.info(String.format("Card with id = %d has been registered.", response.getLibraryCardId()));
            return new SingleResponse<>(response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("crhnbr", lang) + " " + localeService.getString("iid", lang));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("crhnbr", lang) + " " + localeService.getString("sww", lang));
        }
    }
}

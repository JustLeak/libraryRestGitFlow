package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.configuration.annotation.ExceptionLogging;
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
    @Autowired
    private ICardService cardService;
    @Autowired
    private  ILocalizationService bundle;

    //Это сделано потому что CGLIB требует пустой конструктор
    public CardController() {
    }
    
    @GetMapping
    @ExceptionLogging
    public MultiResponseList<LibraryCardDTO> getCards(@RequestParam(required = false) String lang) {
        try {
            List<LibraryCardDTO> response = cardService.getAllCardsDTO();
            logger.info("Cards were shown.");
            return new MultiResponseList<>(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(bundle.getString("crwns", lang) + " " + bundle.getString("sww", lang));
        }
    }

    @GetMapping("/{id}")
    @ExceptionLogging
    public SingleResponse<LibraryCardDTO> getCard(@PathVariable Long id, @RequestParam(required = false) String lang) {
        try {
            LibraryCardDTO response = cardService.getCardDTOById(id);
            logger.info(String.format("Card with id = %d has been shown.", response.getLibraryCardId()));
            return new SingleResponse<>(cardService.getCardDTOById(id));
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(bundle.getString("crhnbf", lang) + " " + bundle.getString("iid", lang));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(bundle.getString("crhnbf", lang) + " " + bundle.getString("sww", lang));
        }
    }

    @PutMapping("/register/{id}")
    @ExceptionLogging
    public SingleResponse<LibraryCardDTO> registerCardByClientId(@PathVariable Long id, @RequestParam(required = false) String lang) {
        try {
            LibraryCardDTO response = cardService.registerCardByClientId(id);
            logger.info(String.format("Card with id = %d has been registered.", response.getLibraryCardId()));
            return new SingleResponse<>(response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(bundle.getString("crhnbr", lang) + " " + bundle.getString("iid", lang));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(bundle.getString("crhnbr", lang) + " " + bundle.getString("sww", lang));
        }
    }
}

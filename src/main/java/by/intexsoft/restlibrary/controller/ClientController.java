package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.response.MultiResponseList;
import by.intexsoft.restlibrary.model.response.SingleResponse;
import by.intexsoft.restlibrary.service.api.IClientService;
import by.intexsoft.restlibrary.service.api.ILocalizationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private static final Logger logger = Logger.getLogger(ClientController.class);
    private final IClientService clientService;
    private final ILocalizationService localeService;

    @Autowired
    public ClientController(IClientService clientService, ILocalizationService localeService) {
        this.clientService = clientService;
        this.localeService = localeService;
    }

    @PostMapping("/new-random")
    public SingleResponse<ClientDTO> generateClient(@RequestParam(required = false) String lang) {
        try {
            ClientDTO response = clientService.generateAndSaveClient();
            logger.info(String.format("Client with id = %d has been generated.", response.getClientId()));
            return new SingleResponse<>(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("clhnbg", lang) + " " + localeService.getString("sww", lang));
        }
    }

    @PostMapping
    public SingleResponse<ClientDTO> newClient(@RequestParam(required = false) String lang, @RequestBody Client newClient) {
        try {
            ClientDTO response = clientService.saveClient(newClient);
            logger.info(String.format("Client with id = %d has been created.", response.getClientId()));
            return new SingleResponse<>(response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("clhnbc", lang) + " " + localeService.getString("iid", lang));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("clhnbc", lang) + " " + localeService.getString("sww", lang));
        }
    }

    @GetMapping
    public MultiResponseList<ClientDTO> getClients(@RequestParam(required = false) String lang) {
        try {
            List<ClientDTO> response = clientService.getAllClientsDTO();
            logger.info(localeService.getString("Clients were shown.", lang));
            return new MultiResponseList<>(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(localeService.getString("clwns", lang) + " " + localeService.getString("sww", lang));
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<ClientDTO> getClient(@PathVariable Long id, @RequestParam(required = false) String lang) {
        try {
            ClientDTO response = clientService.getClientDTOById(id);
            logger.info(String.format("Client with id = %d has been shown.", response.getClientId()));
            return new SingleResponse<>(response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("clhnbf", lang) + " " + localeService.getString("iid", lang));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>(localeService.getString("clhnbf", lang) + " " + localeService.getString("sww", lang));
        }
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id, @RequestParam(required = false) String lang) {
        try {
            clientService.delete(id);
            String response = String.format("Client with id = %d has been deleted.", id);
            logger.info(response);
            return response;
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return localeService.getString("clhnbd", lang) + " " + localeService.getString("iid", lang);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return localeService.getString("clhnbd", lang) + " " + localeService.getString("sww", lang);
        }
    }
}

package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.response.MultiResponseList;
import by.intexsoft.restlibrary.model.response.SingleResponse;
import by.intexsoft.restlibrary.service.api.IClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private static final Logger logger = Logger.getLogger(ClientController.class);
    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/new-random")
    public SingleResponse<ClientDTO> generateClient() {
        try {
            ClientDTO response = clientService.generateAndSaveClient();
            logger.info("Client with id = " + response.getClientId() + " has been generated.");
            return new SingleResponse<>(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Client has not been generated. Something was wrong...");
        }
    }

    @PostMapping
    public SingleResponse<ClientDTO> newClient(@RequestBody Client newClient) {
        try {
            ClientDTO response = clientService.saveClient(newClient);
            logger.info("Client with id = " + response.getClientId() + " has been created.");
            return new SingleResponse<>(response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Client has not been created. Illegal input data.");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Client has not been created. Something was wrong...");
        }
    }

    @GetMapping
    public MultiResponseList<ClientDTO> getClients() {
        try {
            List<ClientDTO> response = clientService.getAllClientsDTO();
            logger.info("Clients were shown.");
            return new MultiResponseList<>(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>("Clients were not shown. Something was wrong...");
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<ClientDTO> getClient(@PathVariable Long id) {
        try {
            ClientDTO response = clientService.getClientDTOById(id);
            logger.info("Client with id = " + response.getClientId() + " has been shown.");
            return new SingleResponse<>(response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Client has not been found. Illegal input data.");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SingleResponse<>("Client has not been found. Something was wrong...");
        }
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        try {
            clientService.delete(id);
            String response = "Client with " + id + " has been deleted.";
            logger.info(response);
            return "response";
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return "Client has not been deleted. Illegal input data.";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "Client has not been deleted. Something was wrong.";
        }
    }
}

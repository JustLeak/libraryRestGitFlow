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
        String message = "Client was generated. ";
        String reason = "";

        try {
            return new SingleResponse<>(clientService.generateAndSaveClient());
        } catch (Exception e) {
            message = "Client not generated. ";
            reason = "Something was wrong...";
            return new SingleResponse<>(message + reason);
        } finally {
            System.out.println(message + reason);
            logger.info(message + reason);
            //TODO логировать message + reason
        }
    }

    @PostMapping
    public SingleResponse<ClientDTO> newClient(@RequestBody Client newClient) {
        String message = "Client was created. ";
        String reason = "";

        try {
            return new SingleResponse<>(clientService.saveClient(newClient));
        } catch (ServiceException e) {
            message = "Client not created. ";
            if (e.getCause() == null)
                reason = e.getMessage();
            else reason = "Something was wrong...";
            return new SingleResponse<>(message + reason);
        } catch (Exception e) {
            message = "Client not created. ";
            reason = "Something was wrong...";
            return new SingleResponse<>(message + reason);
        } finally {
            System.out.println(message + reason);
            logger.info(message + reason);
            //TODO логировать message + reason
        }
    }

    @GetMapping
    public MultiResponseList<ClientDTO> getClients() {
        String message = "Clients were displayed. ";
        String reason = "";

        try {
            return new MultiResponseList<>(clientService.getAllClientsDTO());
        } catch (Exception e) {
            message = "Can't display clients. ";
            reason = "Something was wrong...";
            return new MultiResponseList<>(message + reason);
        } finally {
            System.out.println(message + reason);
            logger.info(message + reason);
            //TODO логировать message + reason
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<ClientDTO> getClient(@PathVariable Long id) {
        String message = "Client was found. ";
        String reason = "";

        try {
            return new SingleResponse<>(clientService.getClientDTOById(id));
        } catch (ServiceException e) {
            message = "Client not found. ";
            if (e.getCause() == null)
                reason = e.getMessage();
            else reason = "Something was wrong...";
            return new SingleResponse<>(message + reason);
        } catch (Exception e) {
            message = "Client not found. ";
            reason = "Something was wrong...";
            return new SingleResponse<>(message + reason);
        } finally {
            System.out.println(message + reason);
            logger.info(message + reason);
            //TODO логировать message + reason
        }
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        String message = "Client was deleted. ";
        String reason = "";

        try {
            clientService.delete(id);
        } catch (ServiceException e) {
            message = "Client not deleted. ";
            if (e.getCause() == null)
                reason = e.getMessage();
            else reason = "Something was wrong...";
        } catch (Exception e) {
            message = "Client not deleted. ";
            reason = "Something was wrong...";
        } finally {
            System.out.println(message + reason);
            logger.info(message + reason);
            //TODO логировать message + reason
            return message + reason;
        }
    }
}

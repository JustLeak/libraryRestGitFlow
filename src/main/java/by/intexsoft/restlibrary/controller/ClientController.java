package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.dto.MultiResponseList;
import by.intexsoft.restlibrary.model.dto.SingleResponse;
import by.intexsoft.restlibrary.service.api.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/new-random")
    public SingleResponse<ClientDTO> generateClient() {
        final String message = "Client not created. ";
        String reason = "Something was wrong...";

        try {
            return new SingleResponse<>(clientService.generateAndSaveClient());
        } catch (Exception e) {
            return new SingleResponse<>(message + reason);
        } finally {
            //TODO логировать message
        }
    }

    @PostMapping
    public SingleResponse<ClientDTO> newClient(@RequestBody Client newClient) {
        final String message = "Client not created. ";
        String reason = "Something was wrong...";

        try {
            return new SingleResponse<>(clientService.saveClient(newClient));
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

    @GetMapping
    public MultiResponseList<ClientDTO> getClients() {
        final String message = "Can't display clients. ";
        String reason = "Something was wrong...";

        try {
            return new MultiResponseList<>(clientService.getAllClientsDTO());
        } catch (Exception e) {
            return new MultiResponseList<>(message + reason);
        } finally {
            //TODO логировать message
        }
    }

    @GetMapping("/{id}")
    public SingleResponse<ClientDTO> getClient(@PathVariable Long id) {
        final String message = "Client not found. ";
        String reason = "Something was wrong...";

        try {
            return new SingleResponse<>(clientService.getClientDTOById(id));
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

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        final String message = "Client not deleted. ";
        String reason = "Something was wrong...";

        try {
            clientService.delete(id);
            return null;
        } catch (ServiceException e) {
            if (e.getCause() == null)
                reason = e.getMessage();

            return message + reason;
        } catch (Exception e) {
            return message + reason;
        } finally {
            //TODO логировать message
        }
    }
}

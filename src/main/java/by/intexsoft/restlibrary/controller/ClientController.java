package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;
import by.intexsoft.restlibrary.service.api.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/new-random")
    public ClientDTO generateClient() {
        return clientService.generateAndSaveClient();
    }

    @PostMapping
    public ClientDTO newClient(@RequestBody Client newClient) {
        return clientService.saveClient(newClient).orElse(null);
    }

    @PutMapping("/{id}/register-card")
    public LibraryCardDTO registerCard(@RequestBody LibraryCard newCard, @PathVariable Long id) {
        System.out.println(newCard.getStartDate());
        return clientService.registerCardByClientId(id, newCard).orElse(null);
    }

    @GetMapping
    public List<ClientDTO> getClients() {
        return clientService.getAllClientsDTO();
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTOById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }
}

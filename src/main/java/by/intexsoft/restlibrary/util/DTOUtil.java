package by.intexsoft.restlibrary.util;

import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;

public class DTOUtil {
    public static ClientDTO clientToDTO(Client client) {
        ClientDTO dto = new ClientDTO();

        if (client.getName() != null)
            dto.setName(client.getName());

        if (client.getLibraryCard() != null && client.getLibraryCard().getId() != null)
            dto.setLibraryCardId(client.getLibraryCard().getId());

        if (client.getSurname() != null)
            dto.setSurname(client.getSurname());

        if (client.getBirthday() != null)
            dto.setBirthday(client.getBirthday().toString());

        if (client.getId() != null)
            dto.setClientId(client.getId());

        return dto;
    }

    public static LibraryCardDTO cardToDTO(LibraryCard libraryCard) {
        LibraryCardDTO dto = new LibraryCardDTO();

        if (libraryCard.getId() != null)
            dto.setLibraryCardId(libraryCard.getId());

        if (libraryCard.getClient() != null && libraryCard.getClient().getId() != null)
            dto.setClientId(libraryCard.getClient().getId());

        return dto;
    }
}

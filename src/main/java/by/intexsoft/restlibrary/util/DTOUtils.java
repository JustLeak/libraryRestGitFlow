package by.intexsoft.restlibrary.util;

import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.LibraryCard;
import by.intexsoft.restlibrary.model.dto.BookDTO;
import by.intexsoft.restlibrary.model.dto.ClientDTO;
import by.intexsoft.restlibrary.model.dto.LibraryCardDTO;

public class DTOUtils {
    public static ClientDTO convertClientToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setName(client.getName());
        if (client.getLibraryCard() != null) {
            dto.setLibraryCardId(client.getLibraryCard().getId());
        }
        dto.setSurname(client.getSurname());
        dto.setBirthday(client.getBirthday().toString());
        dto.setClientId(client.getId());
        return dto;
    }

    public static LibraryCardDTO convertCardToDTO(LibraryCard libraryCard) {
        LibraryCardDTO dto = new LibraryCardDTO();
        dto.setLibraryCardId(libraryCard.getId());
        if (libraryCard.getClient().getId() != null) {
            dto.setClientId(libraryCard.getClient().getId());
        }
        dto.setStartDate(libraryCard.getStartDate().toString());
        return dto;
    }

    public static BookDTO convertBookToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setAuthors(book.getAuthors());
        dto.setName(book.getName());
        dto.setGenre(book.getGenre());
        dto.setId(book.getId());
        if (book.getReleaseDate() != null) {
            dto.setReleaseDate(book.getReleaseDate().toString());
        }
        return dto;
    }
}

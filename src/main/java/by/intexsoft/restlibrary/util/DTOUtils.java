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

        if (client.getName() != null) {
            dto.setName(client.getName());
        }
        if (client.getLibraryCard() != null && client.getLibraryCard().getId() != null) {
            dto.setLibraryCardId(client.getLibraryCard().getId());
        }
        if (client.getSurname() != null) {
            dto.setSurname(client.getSurname());
        }
        if (client.getBirthday() != null) {
            dto.setBirthday(client.getBirthday().toString());
        }
        if (client.getId() != null) {
            dto.setClientId(client.getId());
        }
        return dto;
    }

    public static LibraryCardDTO convertCardToDTO(LibraryCard libraryCard) {
        LibraryCardDTO dto = new LibraryCardDTO();

        if (libraryCard.getId() != null) {
            dto.setLibraryCardId(libraryCard.getId());
        }
        if (libraryCard.getClient().getId() != null) {
            dto.setClientId(libraryCard.getClient().getId());
        }
        if (libraryCard.getStartDate() != null) {
            dto.setStartDate(libraryCard.getStartDate().toString());
        }
        return dto;
    }

    //todo convertBookToDTO
    public static BookDTO convertBookToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setAuthors(book.getAuthors());
        dto.setBookAccounting(book.getBookAccounting());
        dto.setGenre(book.getGenre());
        dto.setId(book.getId());
        dto.setReleaseDate(book.getReleaseDate().toLocalDate());
        return dto;
    }
}
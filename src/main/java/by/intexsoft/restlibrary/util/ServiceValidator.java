package by.intexsoft.restlibrary.util;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Client;
import by.intexsoft.restlibrary.model.LibraryCard;

public class ServiceValidator {
    public static void isValidClientOrThrow(Client client) throws ServiceException {
        if (client == null)
            throw new ServiceException("Illegal input data.", new NullPointerException("Client object is null."));

        if (client.getName() == null)
            throw new ServiceException("Illegal input data.", new NullPointerException("Client.getName() result is null."));
        if (client.getName().trim().equals(""))
            throw new ServiceException("Client name can't be empty.");

        if (client.getSurname() == null)
            throw new ServiceException("Illegal input data.", new NullPointerException("Client.getSurname() result is null."));
        if (client.getSurname().trim().equals(""))
            throw new ServiceException("Client surname can't be empty.");
    }

    public static void isValidIdOrThrow(Long id) throws ServiceException {
        if (id == null)
            throw new ServiceException("Client id must be not null.", new NullPointerException("Client id is null."));
        if (id < 0)
            throw new ServiceException("Client id must be >= 0.", new IllegalArgumentException("Client id = " + id + "."));
    }

    public static void isValidCardOrThrow(LibraryCard card) throws ServiceException {
        if (card == null)
            throw new ServiceException("Illegal input data.", new NullPointerException("LibraryCard object is null."));
        if (card.getClient() == null)
            throw new ServiceException("Illegal input data.", new NullPointerException("LibraryCard.getClient() result is null."));

        if (card.getStartDate() == null)
            throw new ServiceException("Library card must have a registration date.");
    }
}

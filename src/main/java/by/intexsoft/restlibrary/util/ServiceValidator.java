package by.intexsoft.restlibrary.util;

import java.util.Date;

public class ServiceValidator {
    public static boolean isValidClientName(String name) {
        return name != null && !name.trim().equals("");
    }

    public static boolean isValidClientSurname(String surname) {
        return surname != null && !surname.trim().equals("");
    }

    public static boolean isValidId(Long id) {
        return id != null && id >= 0;
    }

    public static boolean isValidLibraryCardRegistrationDate(Date date) {
        return date != null && date.before(new Date());
    }
}

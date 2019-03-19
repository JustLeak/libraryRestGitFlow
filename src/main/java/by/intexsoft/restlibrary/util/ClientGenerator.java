package by.intexsoft.restlibrary.util;

import by.intexsoft.restlibrary.model.Client;

import java.util.Date;

public class ClientGenerator {
    public static Client generate() {
        return new Client(generateStr(), generateStr(), getCurrentDate());
    }

    private static char generateChar() {
        return (char) ((Math.random() * 25) + 'a');
    }

    private static String generateStr() {
        int length = (int) (Math.random() * 10);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append(generateChar());
        return builder.toString();
    }

    private static Date getCurrentDate() {
        return new Date();
    }
}

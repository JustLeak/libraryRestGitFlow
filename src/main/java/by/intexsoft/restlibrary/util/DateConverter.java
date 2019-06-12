package by.intexsoft.restlibrary.util;

import java.sql.Date;

public class DateConverter {
    public static final String DATE_FORMAT = "yyyy-[m]m-[d]d";

    public static Date convertFrom(String date) {
        try {
            return Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal date value: " + date + ". Required date format: " + DATE_FORMAT + ".");
        }
    }
}

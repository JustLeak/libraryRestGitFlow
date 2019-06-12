package by.intexsoft.restlibrary.model.filter;

import by.intexsoft.restlibrary.model.enumeration.Genre;
import by.intexsoft.restlibrary.util.DateConverter;

import java.sql.Date;

public class BookFilter {
    private String name;
    private Genre genre;
    private Date from;
    private Date to;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = Genre.forString(genre);
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = DateConverter.convertFrom(from);
    }

    public Date getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = DateConverter.convertFrom(to);
    }
}

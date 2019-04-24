package by.intexsoft.restlibrary.model.filter;

import by.intexsoft.restlibrary.model.enumeration.Genre;
import by.intexsoft.restlibrary.util.DateConverter;

import java.sql.Date;

public class BookFilter {
    private String name;
    private Genre genre;
    private Date from;
    private Date to;

    private BookFilter(Builder builder) {
        this.name = builder.name;
        this.genre = builder.genre;
        this.from = builder.from;
        this.to = builder.to;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "BookFilter{" +
                "name='" + name + '\'' +
                ", genre=" + genre +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public static final class Builder {
        private String name;
        private Genre genre;
        private Date from;
        private Date to;

        private Builder() {
        }

        public Builder filterByGenre(String genre) {
            if (genre != null) {
                this.genre = Genre.forString(genre);
            }
            return this;
        }

        public Builder filterByDate(String from, String to) {
            if (from != null) {
                this.from = DateConverter.convertFrom(from);
            }
            if (to != null) {
                this.to = DateConverter.convertFrom(to);
            }
            return this;
        }

        public Builder filterByStartDate(String from) {
            if (from != null) {
                this.from = DateConverter.convertFrom(from);
            }
            return this;
        }

        public Builder filterByEndDate(String to) {
            if (to != null) {
                this.to = DateConverter.convertFrom(to);
            }
            return this;
        }

        public Builder filterByName(String name) {
            if (name != null) {
                this.name = name;
            }
            return this;
        }

        public BookFilter build() {
            return new BookFilter(this);
        }
    }
}

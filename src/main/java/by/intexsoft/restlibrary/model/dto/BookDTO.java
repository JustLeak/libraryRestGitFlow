package by.intexsoft.restlibrary.model.dto;

import by.intexsoft.restlibrary.model.Author;
import by.intexsoft.restlibrary.model.BookAccounting;
import by.intexsoft.restlibrary.model.enumeration.Genre;

import java.time.LocalDate;
import java.util.Set;

public class BookDTO {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private Genre genre;
    private BookAccounting bookAccounting;
    private Set<Author> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public BookAccounting getBookAccounting() {
        return bookAccounting;
    }

    public void setBookAccounting(BookAccounting bookAccounting) {
        this.bookAccounting = bookAccounting;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}

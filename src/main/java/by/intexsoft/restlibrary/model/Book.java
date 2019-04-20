package by.intexsoft.restlibrary.model;

import by.intexsoft.restlibrary.model.enumeration.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private Date releaseDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Record record;

    @OneToOne(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private BookAccounting bookAccounting;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_author_id"))
    private Set<Author> authors;

    public Book(String name, Genre genre, Date releaseDate, Set<Author> authors) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.authors = authors;
    }

    public Book() {
    }


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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre=" + genre +
                ", record=" + record +
                ", bookAccounting=" + bookAccounting +
                ", authors=" + authors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        if (!name.equals(book.name)) return false;
        return authors.equals(book.authors);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + authors.hashCode();
        return result;
    }
}

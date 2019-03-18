package by.intexsoft.restlibrary.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "author_id"))
})
public class Author extends Person {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "author_author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_book_id"))
    private Set<Book> books;

    public Author() {
    }
}

package by.intexsoft.restlibrary.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "author_id"))
})
public class Author extends Person {

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> books;

    public Author(String name, String surname) {
        super(name, surname);
    }

    public Author() {
    }
}

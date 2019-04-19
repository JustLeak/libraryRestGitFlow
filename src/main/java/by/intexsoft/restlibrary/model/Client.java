package by.intexsoft.restlibrary.model;

import by.intexsoft.restlibrary.util.ClientGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "client_id"))
})
public class Client extends Person {

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private LibraryCard libraryCard;

    public Client() {
    }

    public Client(String name, String surname, Date birthday) {
        super(name, surname, birthday);
    }

    public static Client random() {
        return ClientGenerator.generate();
    }

    public LibraryCard getLibraryCard() {
        return libraryCard;
    }
}

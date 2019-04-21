package by.intexsoft.restlibrary.model;

import javax.persistence.*;

@Entity
@Table(name = "book_accounting")
public class BookAccounting {
    @Id
    @Column(name = "book_book_id")
    private Long id;

    @Column(name = "total")
    private Long total;

    @Column(name = "available")
    private Long available;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private Book book;

    public BookAccounting() {
    }

    public BookAccounting(Book book) {
        total = 1L;
        available = 1L;
        this.book = book;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "BookAccounting{" +
                "id=" + id +
                ", total=" + total +
                ", available=" + available +
                '}';
    }
}

package by.intexsoft.restlibrary.service.loader;

import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.BookAccounting;
import by.intexsoft.restlibrary.service.loader.api.IBookLoader;
import by.intexsoft.restlibrary.service.parser.api.IBookParser;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BookLoader implements IBookLoader {
    protected final IBookParser bookParser;

    protected BookLoader(IBookParser bookParser) {
        this.bookParser = bookParser;
    }

    protected abstract Book create(List<String> strList);

    protected abstract Collection<List<String>> readBooks() throws IOException;

    @Override
    public Set<Book> loadBooks() throws IOException {
        Collection<List<String>> books = readBooks();
        return books.stream()
                .map(this::create)
                .collect(Collectors.toMap(book -> book, Book::getBookAccounting, this::updateBookAccounting))
                .keySet();
    }

    private BookAccounting updateBookAccounting(BookAccounting oldBookAccounting, BookAccounting newBookAccounting) {
        Book oldBook = oldBookAccounting.getBook();
        Book newBook = newBookAccounting.getBook();
        if (!isValidBooksToJoin(oldBook, newBook)) {
            throw new IllegalArgumentException("Books with the same name and author must have the same genre and release date.");
        }
        Long newCount = oldBookAccounting.getTotal() + 1;
        oldBookAccounting.setTotal(newCount);
        oldBookAccounting.setAvailable(newCount);
        return oldBookAccounting;
    }

    private boolean isValidBooksToJoin(Book oldBook, Book newBook) {
        if (!oldBook.getGenre().equals(newBook.getGenre())) {
            return false;
        }
        return oldBook.getReleaseDate() != null ? oldBook.getReleaseDate().equals(newBook.getReleaseDate()) : newBook.getReleaseDate() == null;
    }
}

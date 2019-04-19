package by.intexsoft.restlibrary.service.loader.buffer;

public class BookBuffer {
    private String name;
    private String description;
    private String date;
    private String authors;

    public BookBuffer(String name, String description, String date, String authors) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getAuthors() {
        return authors;
    }
}

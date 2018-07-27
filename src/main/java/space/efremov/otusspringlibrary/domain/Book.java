package space.efremov.otusspringlibrary.domain;

import java.util.List;

public class Book extends Entity {

    private final String title;
    private final String isbn;
    private final int year;
    private final Publish publish;
    private final List<Tag> tags;
    private final List<Author> authors;

    public Book(Long id, String title, String isbn, int year, Publish publish, List<Tag> tags, List<Author> authors) {
        super(id);
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.publish = publish;
        this.tags = tags;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYear() {
        return year;
    }

    public Publish getPublish() {
        return publish;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}

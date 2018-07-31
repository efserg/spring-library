package space.efremov.otusspringlibrary.domain;

import java.util.List;

public class Book extends Entity {

    private final String title;
    private final String isbn;
    private final int year;
    private final Publisher publisher;
    private final List<Tag> tags;
    private final List<Author> authors;

    public Book(Integer id, String title, String isbn, int year, Publisher publisher, List<Tag> tags, List<Author> authors) {
        super(id);
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.publisher = publisher;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append(", year=").append(year);
        sb.append(", publisher=").append(publisher);
        sb.append(", tags=").append(tags);
        sb.append(", authors=").append(authors);
        sb.append('}');
        return sb.toString();
    }
}

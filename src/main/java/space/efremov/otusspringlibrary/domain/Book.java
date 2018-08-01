package space.efremov.otusspringlibrary.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
public class Book extends AbstractEntity {

    @Column(name = "title")
    private final String title;

    @Column(name = "isbn")
    private final String isbn;

    @Column(name = "year")
    private final int year;

    @ManyToOne(targetEntity = Publisher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", foreignKey = @ForeignKey(name = "FK_book_publisher"))
    private final Publisher publisher;

    @ManyToMany(targetEntity = Tag.class)
    @JoinTable(name = "tag_books", joinColumns = {@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_tag_books_tag"))}, inverseJoinColumns = {@JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "FK_tag_books_book"))})
    private final List<Tag> tags;

    @ManyToMany(targetEntity = Author.class)
    @JoinTable(name = "author_books", joinColumns = {@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_author_books_book"))}, inverseJoinColumns = {@JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_author_books_author"))})
    private final List<Author> authors;

    @OneToMany(mappedBy = "book")
    private final List<Review> reviews;

    public Book(Integer id, String title, String isbn, int year, Publisher publisher, List<Tag> tags, List<Author> authors, List<Review> reviews) {
        super(id);
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.publisher = publisher;
        this.tags = tags;
        this.authors = authors;
        this.reviews = reviews;
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

    public List<Review> getReviews() {
        return reviews;
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

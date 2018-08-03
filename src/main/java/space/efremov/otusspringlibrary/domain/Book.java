package space.efremov.otusspringlibrary.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Book extends AbstractEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "year")
    private int year;

    @ManyToOne(targetEntity = Publisher.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", foreignKey = @ForeignKey(name = "FK_book_publisher"))
    private Publisher publisher;

    @ManyToMany(targetEntity = Tag.class)
    @JoinTable(name = "tag_books", joinColumns = {@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_tag_books_tag"))}, inverseJoinColumns = {@JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "FK_tag_books_book"))})
    private List<Tag> tags;

    @ManyToMany(targetEntity = Author.class)
    @JoinTable(name = "author_books", joinColumns = {@JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_author_books_book"))}, inverseJoinColumns = {@JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_author_books_author"))})
    private List<Author> authors;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<Review> reviews;

    public Book(String title, String isbn, int year, Publisher publisher, List<Tag> tags, List<Author> authors) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.publisher = publisher;
        this.tags = tags;
        this.authors = authors;
    }

    public Book(String title, String isbn, int year, Publisher publisher) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.publisher = publisher;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return year == book.year &&
                Objects.equals(title, book.title) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(publisher, book.publisher);
    }

    public void addReview(Review review) {
        review.setBook(this);
        reviews.add(review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn, year, publisher);
    }
}

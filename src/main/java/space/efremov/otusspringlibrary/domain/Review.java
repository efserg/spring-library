package space.efremov.otusspringlibrary.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review extends AbstractEntity {

    @ManyToOne(targetEntity = Person.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "FK_book_person"))
    private final Person person;

    @ManyToOne(targetEntity = Book.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_review_book"))
    private final Book book;

    @Column(name = "review_date")
    private final LocalDateTime reviewDate;

    @Column(name = "text", length = 1024)
    private final String text;

    public Review(Integer id, Person person, Book book, LocalDateTime reviewDate, String text) {
        super(id);
        this.person = person;
        this.book = book;
        this.reviewDate = reviewDate;
        this.text = text;
    }

    public Person getPerson() {
        return person;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public String getText() {
        return text;
    }
}

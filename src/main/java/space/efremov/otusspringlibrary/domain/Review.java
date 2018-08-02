package space.efremov.otusspringlibrary.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review extends AbstractEntity {

    @ManyToOne(targetEntity = Person.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "FK_book_person"))
    private Person person;

    @ManyToOne(targetEntity = Book.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_review_book"))
    private Book book;

    @Column(name = "review_date")
    @CreationTimestamp
    private LocalDateTime reviewDate;

    @Column(name = "text", length = 1024)
    private String text;

    private Review() {
    }

    public Review(Person person, Book book, String text) {
        this.person = person;
        this.book = book;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(person, review.person) &&
                Objects.equals(book, review.book) &&
                Objects.equals(reviewDate, review.reviewDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, book, reviewDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("person=").append(person);
        sb.append(", book=").append(book);
        sb.append(", reviewDate=").append(reviewDate);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package space.efremov.otusspringlibrary.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Review extends AbstractEntity {

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "FK_book_person"))
    private User user;

    @ManyToOne(targetEntity = Book.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_review_book"))
    private Book book;

    @Column(name = "review_date")
    @CreationTimestamp
    private LocalDateTime reviewDate;

    @Column(name = "text", length = 1024)
    private String text;

    public Review(User user, String text) {
        this.user = user;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return id != null && Objects.equals(id, review.id) ||
                Objects.equals(user, review.user) &&
                Objects.equals(book, review.book) &&
                Objects.equals(reviewDate, review.reviewDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, book, reviewDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("user=").append(user);
        sb.append(", reviewDate=").append(reviewDate);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

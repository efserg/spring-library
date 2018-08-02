package space.efremov.otusspringlibrary.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}

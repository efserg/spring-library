package space.efremov.otusspringlibrary.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "person")
public class Person extends AbstractEntity {

    @Email
    @Column(name = "email")
    private final String email;

    @Column(name = "username")
    private final String username;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private final List<Review> reviews;

    public Person(Long id, @Email String email, String username, List<Review> reviews) {
        super(id);
        this.email = email;
        this.username = username;
        this.reviews = reviews;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}

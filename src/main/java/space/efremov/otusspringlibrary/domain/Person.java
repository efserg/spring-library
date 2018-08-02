package space.efremov.otusspringlibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends AbstractEntity {

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<Review> reviews;

    private Person() {
    }

    public Person(@Email String email, String username) {
        this.email = email;
        this.username = username;
    }
}

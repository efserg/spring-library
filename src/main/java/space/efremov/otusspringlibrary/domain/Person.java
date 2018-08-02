package space.efremov.otusspringlibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(email, person.email) &&
                Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("email='").append(email).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package space.efremov.otusspringlibrary.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends AbstractEntity {

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Review> reviews;

    public User(@Email String email, String username) {
        this.email = email;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

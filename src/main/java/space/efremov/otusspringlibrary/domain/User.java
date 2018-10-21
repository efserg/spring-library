package space.efremov.otusspringlibrary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.efremov.otusspringlibrary.config.SecurityConfig;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
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
    @JsonIgnore
    private List<Review> reviews;

    @Size(max = 255)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(@Email String email, String username, @Size(max = 255) String password, UserRole role) {
        this.email = email;
        this.username = username;
        this.password = SecurityConfig.passwordEncoder.encode(password);
        this.role = role;
    }

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

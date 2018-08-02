package space.efremov.otusspringlibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
public class Author extends NamedEntity {

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    private Author() {
    }

    public Author(String name) {
        super(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

package space.efremov.otusspringlibrary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class Tag extends NamedEntity {

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Tag(String name) {
        super(name);
    }

    private Tag() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

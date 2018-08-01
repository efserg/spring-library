package space.efremov.otusspringlibrary.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "author")
public class Author extends NamedEntity {

    @ManyToMany(mappedBy = "authors")
    private final List<Book> books;

    public Author(Long id, String name, List<Book> books) {
        super(id, name);
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
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

package space.efremov.otusspringlibrary.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "publisher")
public class Publisher extends NamedEntity {

    public Publisher(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Publisher{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

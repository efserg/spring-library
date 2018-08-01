package space.efremov.otusspringlibrary.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity extends AbstractEntity {

    @Column(name = "name")
    protected final String name;

    public NamedEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

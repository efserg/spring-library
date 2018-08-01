package space.efremov.otusspringlibrary.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected final Long id;

    public AbstractEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

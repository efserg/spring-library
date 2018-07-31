package space.efremov.otusspringlibrary.domain;

public abstract class Entity {

    protected final Integer id;

    public Entity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

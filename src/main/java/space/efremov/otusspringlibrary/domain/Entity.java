package space.efremov.otusspringlibrary.domain;

public abstract class Entity {
    private final Long id;

    public Entity(Long id) {
        this.id = id;
    }
}

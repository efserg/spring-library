package space.efremov.otusspringlibrary.domain;

public abstract class NamedEntity extends Entity {

    protected final String name;

    public NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

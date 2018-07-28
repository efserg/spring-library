package space.efremov.otusspringlibrary.domain;

public class Tag extends Entity {

    private final String name;

    public Tag(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

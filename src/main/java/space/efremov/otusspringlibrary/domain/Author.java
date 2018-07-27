package space.efremov.otusspringlibrary.domain;

public class Author extends Entity {

    private final String name;

    public Author(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

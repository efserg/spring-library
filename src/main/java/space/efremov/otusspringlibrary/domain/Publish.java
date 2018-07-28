package space.efremov.otusspringlibrary.domain;

public class Publish extends Entity {

    private final String name;


    public Publish(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package space.efremov.otusspringlibrary.exception;

public class EntityNotFoundException extends RuntimeException {

    private Class clazz;

    private Long id;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(Class clazz, Long id) {
        this.clazz = clazz;
        this.id = id;
    }

    public Class getClazz() {
        return clazz;
    }

    public Long getId() {
        return id;
    }

}

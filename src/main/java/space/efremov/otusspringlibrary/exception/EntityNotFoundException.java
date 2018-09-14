package space.efremov.otusspringlibrary.exception;

import java.util.Optional;

public class EntityNotFoundException extends RuntimeException {

    private Class clazz;

    private Long id;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(Class<?> clazz, Long id) {
        this.clazz = clazz;
        this.id = id;
    }

    public EntityNotFoundException(Class<?> clazz) {
        this(clazz, null);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }
}

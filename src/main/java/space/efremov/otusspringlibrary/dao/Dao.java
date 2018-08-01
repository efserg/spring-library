package space.efremov.otusspringlibrary.dao;

import space.efremov.otusspringlibrary.domain.AbstractEntity;

import java.util.List;

public interface Dao<E extends AbstractEntity> {

    Long count();

    E insert(E entity);

    void delete(Long id);

    E getById(Long id);

    List<E> getAll();

}

package space.efremov.otusspringlibrary.dao;

import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;

public interface Dao<E> {

    Integer count();

    E insert(E entity);

    void delete(E entity);

    E getById(Integer id) throws EntityNotFoundException;

    List<E> getAll();

}

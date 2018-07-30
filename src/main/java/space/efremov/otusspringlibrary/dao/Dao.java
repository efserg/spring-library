package space.efremov.otusspringlibrary.dao;

import java.util.List;

public interface Dao<E> {

    Integer count();

    E insert(E entity);

    void delete(Integer id);

    E getById(Integer id);

    List<E> getAll();

}

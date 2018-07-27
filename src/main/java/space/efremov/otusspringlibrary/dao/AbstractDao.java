package space.efremov.otusspringlibrary.dao;

import space.efremov.otusspringlibrary.domain.Entity;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

public interface AbstractDao<E extends Entity> {

    Integer count();

    Integer insert(E entity);

    E getById(Long id) throws EntityNotFoundException;

}

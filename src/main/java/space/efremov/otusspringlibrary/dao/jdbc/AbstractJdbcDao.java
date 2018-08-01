package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.AbstractEntity;

public abstract class AbstractJdbcDao<E extends AbstractEntity> implements Dao<E> {

    protected final NamedParameterJdbcOperations jdbc;
    protected final EntityToMapConverter converter;

    public AbstractJdbcDao(NamedParameterJdbcOperations jdbc, EntityToMapConverter converter) {
        this.jdbc = jdbc;
        this.converter = converter;
    }
}

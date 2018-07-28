package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PublishDao extends AbstractJdbcDao<Publisher> {

    private final RowMapper<Publisher> rowMapper = (RowMapper<Publisher>) (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Publisher(id, name);
    };

    protected PublishDao(NamedParameterJdbcOperations jdbc, EntityToMapConverter converter) {
        super(jdbc, converter);
    }

    @Override
    public Integer count() {
        return jdbc.queryForObject("select count(*) from publisher", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public void insert(Publisher entity) {
        jdbc.update("insert into publisher (id, name) values (:id, :name)", converter.convert(entity));
    }

    @Override
    public void delete(Publisher entity) {
        final Map<String, Object> idParam = converter.getIdParam(entity.getId());
        jdbc.update("delete from book where publish_id = :id", idParam);
        jdbc.update("delete from publisher where id = :id", idParam);
    }

    @Override
    public void update(Publisher entity) {
        jdbc.update("update publisher set name = :name where id = :id", converter.convert(entity));

    }

    @Override
    public Publisher getById(Integer id) throws EntityNotFoundException {
        return jdbc.queryForObject("select * from publisher where id = :id", converter.getIdParam(id), rowMapper);
    }

    @Override
    public List<Publisher> getAll() {
        final List<Map> rows = jdbc.queryForList("select * from publisher", Collections.EMPTY_MAP);
        return rows.stream().map(m -> new Publisher((Integer) m.get("id"), (String) m.get("name"))).collect(Collectors.toList());
    }
}

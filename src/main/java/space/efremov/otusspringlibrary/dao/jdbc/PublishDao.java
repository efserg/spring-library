package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.jdbc.mapper.PublishRowMapper;
import space.efremov.otusspringlibrary.domain.Publish;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PublishDao extends AbstractJdbcDao<Publish> {

    private final PublishRowMapper rowMapper = new PublishRowMapper();

    protected PublishDao(NamedParameterJdbcOperations jdbc, EntityToMapConverter converter) {
        super(jdbc, converter);
    }

    @Override
    public Integer count() {
        return jdbc.queryForObject("select count(*) from publish", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public void insert(Publish entity) {
        jdbc.update("insert into publish (id, name) values (:id, :name)", converter.convert(entity));
    }

    @Override
    public void delete(Publish entity) {
        final Map<String, Object> idParam = converter.getIdParam(entity.getId());
        jdbc.update("delete from book where publish_id = :id", idParam);
        jdbc.update("delete from publish where id = :id", idParam);
    }

    @Override
    public void update(Publish entity) {
        jdbc.update("update publish set name = :name where id = :id", converter.convert(entity));

    }

    @Override
    public Publish getById(Integer id) throws EntityNotFoundException {
        return jdbc.queryForObject("select * from publish where id = :id", converter.getIdParam(id), rowMapper);
    }

    @Override
    public List<Publish> getAll() {
        final List<Map> rows = jdbc.queryForList("select * from publish", Collections.EMPTY_MAP);
        return rows.stream().map(m -> new Publish((Integer) m.get("id"), (String) m.get("name"))).collect(Collectors.toList());
    }
}

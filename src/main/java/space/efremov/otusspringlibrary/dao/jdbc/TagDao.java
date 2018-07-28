package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TagDao extends AbstractJdbcDao<Tag> {

    private final RowMapper<Tag> rowMapper = (RowMapper<Tag>) (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Tag(id, name);
    };

    protected TagDao(NamedParameterJdbcOperations jdbc, EntityToMapConverter converter) {
        super(jdbc, converter);
    }

    @Override
    public Integer count() {
        return jdbc.queryForObject("select count(*) from book_tag", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public void insert(Tag entity) {
        jdbc.update("insert into book_tag (id, name) values (:id, :name)", converter.convert(entity));
    }

    @Override
    public void delete(Tag entity) {
        final Map<String, Object> idParam = converter.getIdParam(entity.getId());
        jdbc.update("delete from book_has_book_tag where book_tag_id = :id", idParam);
        jdbc.update("delete from book_tag where id = :id", idParam);
    }

    @Override
    public void update(Tag entity) {
        jdbc.update("update book_tag set name = :name where id = :id", converter.convert(entity));
    }

    @Override
    public Tag getById(Integer id) throws EntityNotFoundException {
        return jdbc.queryForObject("select * from book_tag where id = :id", converter.getIdParam(id), rowMapper);
    }

    @Override
    public List<Tag> getAll() {
        final List<Map> rows = jdbc.queryForList("select * from book_tag", Collections.EMPTY_MAP);
        return rows.stream().map(m -> new Tag((Integer) m.get("id"), (String) m.get("name"))).collect(Collectors.toList());
    }
}

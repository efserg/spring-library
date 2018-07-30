package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class AuthorDao extends AbstractJdbcDao<Author> {

    private final RowMapper<Author> rowMapper = (RowMapper<Author>) (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Author(id, name);
    };

    protected AuthorDao(NamedParameterJdbcOperations jdbc, EntityToMapConverter converter) {
        super(jdbc, converter);
    }

    @Override
    public Integer count() {
        return jdbc.queryForObject("select count(*) from author", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public Author insert(Author entity) {
        jdbc.update("insert into author (id, name) values (:id, :name)", converter.convert(entity));
        return entity;
    }

    @Override
    public void delete(Author entity) {
        final Map<String, Object> idParam = converter.getIdParam(entity.getId());
        jdbc.update("delete from book_has_author where author_id = :id", idParam);
        jdbc.update("delete from author where id = :id", idParam);
    }

    @Override
    public Author getById(Integer id) throws EntityNotFoundException {
        return jdbc.queryForObject("select * from author where id = :id", converter.getIdParam(id), rowMapper);
    }

    @Override
    public List<Author> getAll() {
        final List<Map> rows = jdbc.queryForList("select * from author", Collections.EMPTY_MAP);
        return rows.stream().map(m -> new Author((Integer) m.get("id"), (String) m.get("name"))).collect(Collectors.toList());
    }

}

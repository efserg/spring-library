package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.jdbc.mapper.AuthorRowMapper;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class AuthorDao extends AbstractJdbcDao<Author> {

    private final AuthorRowMapper rowMapper = new AuthorRowMapper();

    protected AuthorDao(NamedParameterJdbcOperations jdbc, EntityToMapConverter converter) {
        super(jdbc, converter);
    }

    @Override
    public Integer count() {
        return jdbc.queryForObject("select count(*) from author", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public void insert(Author entity) {
        jdbc.update("insert into author (id, name) values (:id, :name)", converter.convert(entity));
    }

    @Override
    public void delete(Author entity) {
        jdbc.update("delete from author where id = :id", converter.getIdParam(entity.getId()));
    }

    @Override
    public void update(Author entity) {
        jdbc.update("update author set name = :name where id = :id", converter.convert(entity));

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

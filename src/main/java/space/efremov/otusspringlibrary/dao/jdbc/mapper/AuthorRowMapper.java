package space.efremov.otusspringlibrary.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import space.efremov.otusspringlibrary.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Author(id, name);
    }
}
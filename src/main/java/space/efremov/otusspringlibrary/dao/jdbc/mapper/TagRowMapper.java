package space.efremov.otusspringlibrary.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import space.efremov.otusspringlibrary.domain.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Tag(id, name);
    }
}
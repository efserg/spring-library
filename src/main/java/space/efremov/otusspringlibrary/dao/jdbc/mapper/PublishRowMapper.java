package space.efremov.otusspringlibrary.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import space.efremov.otusspringlibrary.domain.Publish;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublishRowMapper implements RowMapper<Publish> {

    @Override
    public Publish mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Publish(id, name);
    }
}
package space.efremov.otusspringlibrary.dao;

import org.junit.Test;
import space.efremov.otusspringlibrary.dao.jdbc.EntityToMapConverter;
import space.efremov.otusspringlibrary.domain.Author;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityToMapConverterTest {

    private final EntityToMapConverter converter = new EntityToMapConverter();

    @Test
    public void convertAuthor() {
        Author author = new Author(1L, "Alex", Collections.emptyList());
        final Map<String, Object> map = converter.convert(author);
        assertTrue(map.containsKey("id"));
        assertTrue(map.containsKey("name"));
        assertEquals(map.get("id"), 1);
        assertEquals(map.get("name"), "Alex");
    }
}
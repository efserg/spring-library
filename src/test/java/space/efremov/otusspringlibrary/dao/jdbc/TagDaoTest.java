package space.efremov.otusspringlibrary.dao.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class,
        properties = {
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TagDaoTest {

    @Autowired
    private TagDao dao;

    @Test
    public void count() {
        assertEquals(dao.count().longValue(), 3);
    }

    @Test
    public void insert() {
        final long id = 2000;
        dao.insert(new Tag(id, "Sci-learn", Collections.emptyList()));
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "Sci-learn");
        dao.delete(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete() {
        final long id = 3000;
        dao.insert(new Tag(id, "Spring", Collections.emptyList()));
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "Spring");
        dao.delete(id);
        dao.getById(id);
    }

    @Test
    public void getById() {
        final long id = 1;
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "Java");
    }

    @Test(expected = EntityNotFoundException.class)
    public void eGetByIdExceptionCheck() {
        dao.getById(4000L);
    }

    @Test
    public void getAll() {
        final List<Tag> authors = dao.getAll();
        assertEquals(authors.size(), 3);
        assertTrue(authors.stream().allMatch(a -> a.getId() == 1 || a.getId() == 2 || a.getId() == 3));
        assertTrue(authors.stream().allMatch(a -> Objects.equals(a.getName(), "Java") || Objects.equals(a.getName(), "Big Data") || Objects.equals(a.getName(), "Python")));
    }
}
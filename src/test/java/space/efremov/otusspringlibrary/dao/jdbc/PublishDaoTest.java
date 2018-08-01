package space.efremov.otusspringlibrary.dao.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class,
        properties = {
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PublishDaoTest {

    @Autowired
    private PublishDao dao;

    @Test
    public void count() {
        assertEquals(dao.count().longValue(), 1);
    }

    @Test
    public void insert() {
        final long id = 2000;
        dao.insert(new Publisher(id, "Addison-Wesley"));
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "Addison-Wesley");
        dao.delete(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete() {
        final long id = 3000;
        dao.insert(new Publisher(id, "John Wiley & Sons"));
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "John Wiley & Sons");
        dao.delete(id);
        dao.getById(id);
    }

    @Test
    public void getById() {
        final long id = 1;
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "O'Reilly Media");
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdExceptionCheck() {
        dao.getById(4000L);
    }

    @Test
    public void getAll() {
        final List<Publisher> authors = dao.getAll();
        assertEquals(authors.size(), 1);
        assertTrue(authors.stream().anyMatch(a -> a.getId() == 1L));
        assertTrue(authors.stream().anyMatch(a -> Objects.equals(a.getName(), "O'Reilly Media")));
        assertFalse(authors.stream().anyMatch(a -> a.getId() == 2L));
    }
}
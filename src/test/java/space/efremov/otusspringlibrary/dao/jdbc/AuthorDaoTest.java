package space.efremov.otusspringlibrary.dao.jdbc;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorDaoTest {

    @Autowired
    private AuthorDao dao;

    @Test
    public void count() {
        assertEquals(dao.count().longValue(), 1L);
    }

    @Test
    public void insert() {
        final long id = 2000;
        dao.insert(new Author(id, "Donald Knuth", Collections.emptyList()));
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "Donald Knuth");
        dao.delete(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete() {
        final long id = 3000;
        dao.insert(new Author(id, "Dennis MacAlistair Ritchie", Collections.emptyList()));
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "Dennis MacAlistair Ritchie");
        dao.delete(id);
        dao.getById(id);
    }

    @Test
    public void getById() {
        final long id = 1;
        assertEquals(dao.getById(id).getId().longValue(), id);
        assertEquals(dao.getById(id).getName(), "Aurélien Géron");
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdExceptionCheck() {
        dao.getById(4000L);
    }

    @Test
    public void getAll() {
        final List<Author> authors = dao.getAll();
        assertEquals(authors.size(), 1);
        assertTrue(authors.stream().anyMatch(a -> a.getId() == 1));
        assertTrue(authors.stream().anyMatch(a -> Objects.equals(a.getName(), "Aurélien Géron")));
        assertFalse(authors.stream().anyMatch(a -> a.getId() == 2));
    }
}
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
        assertEquals(dao.count().longValue(), 1);
    }

    @Test
    public void insert() {
        dao.insert(new Author(2000, "Donald Knuth", Collections.emptyList()));
        assertEquals(dao.getById(2000).getId().longValue(), 2000);
        assertEquals(dao.getById(2000).getName(), "Donald Knuth");
        dao.delete(2000);
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete() {
        dao.insert(new Author(3000, "Dennis MacAlistair Ritchie", Collections.emptyList()));
        assertEquals(dao.getById(3000).getId().longValue(), 3000);
        assertEquals(dao.getById(3000).getName(), "Dennis MacAlistair Ritchie");
        dao.delete(3000);
        dao.getById(3000);
    }

    @Test
    public void getById() {
        assertEquals(dao.getById(1).getId().longValue(), 1);
        assertEquals(dao.getById(1).getName(), "Aurélien Géron");
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdExceptionCheck() {
        dao.getById(4000);
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
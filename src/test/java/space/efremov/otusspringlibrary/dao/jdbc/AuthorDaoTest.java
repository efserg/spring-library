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
    private AuthorDao authorDao;

    @Test
    public void aCount() {
        assertEquals(authorDao.count().longValue(), 1);
    }

    @Test
    public void bInsert() {
        authorDao.insert(new Author(2000, "Donald Knuth"));
        assertEquals(authorDao.getById(2000).getId().longValue(), 2000);
        assertEquals(authorDao.getById(2000).getName(), "Donald Knuth");
        authorDao.delete(2000);
    }

    @Test(expected = EntityNotFoundException.class)
    public void cDelete() {
        authorDao.insert(new Author(3000, "Dennis MacAlistair Ritchie"));
        assertEquals(authorDao.getById(3000).getId().longValue(), 3000);
        assertEquals(authorDao.getById(3000).getName(), "Dennis MacAlistair Ritchie");
        authorDao.delete(3000);
        authorDao.getById(3000);
    }

    public void dGetById() {
        assertEquals(authorDao.getById(1).getId().longValue(), 1);
        assertEquals(authorDao.getById(1).getName(), "Aurélien Géron");
    }

    @Test(expected = EntityNotFoundException.class)
    public void eGetByIdExceptionCheck() {
        authorDao.getById(4000);
    }

    @Test
    public void eGetAll() {
        final List<Author> authors = authorDao.getAll();
        assertEquals(authors.size(), 1);
        assertTrue(authors.stream().anyMatch(a -> a.getId() == 1));
        assertTrue(authors.stream().anyMatch(a -> Objects.equals(a.getName(), "Aurélien Géron")));
        assertFalse(authors.stream().anyMatch(a -> a.getId() == 2));
    }
}
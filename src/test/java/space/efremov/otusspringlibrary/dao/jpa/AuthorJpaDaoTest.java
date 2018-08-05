package space.efremov.otusspringlibrary.dao.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.dao.TestConfig;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class,
        properties = {
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class AuthorJpaDaoTest {

    @Autowired
    private AuthorJpaDao dao;

    @Test
    public void count() {
        final Author RICHARD_STALLMAN = new Author("Richard Matthew Stallman");
        assertEquals(dao.count().longValue(), 0);
        dao.insert(RICHARD_STALLMAN);
        assertEquals(dao.count().longValue(), 1);
        dao.delete(RICHARD_STALLMAN);
    }

    @Test
    public void insert() {
        final Author RICHARD_STALLMAN = new Author("Richard Matthew Stallman");
        dao.insert(RICHARD_STALLMAN);
        assertTrue(dao.getAll().contains(RICHARD_STALLMAN));
        assertEquals(dao.count().longValue(), 1);
        dao.delete(RICHARD_STALLMAN);
    }

    @Test
    public void getById() {
        final Author RICHARD_STALLMAN = new Author("Richard Matthew Stallman");
        dao.insert(RICHARD_STALLMAN);
        final Author author = dao.getById(RICHARD_STALLMAN.getId());
        assertEquals(author, RICHARD_STALLMAN);
        dao.delete(RICHARD_STALLMAN);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdNotFoundTest() {
        final Author author = dao.getById(1L);
    }

    @Test
    public void getAll() {
        final Author RICHARD_STALLMAN = new Author("Richard Matthew Stallman");
        final Author DENNIS_RITCHIE = new Author("Dennis MacAlistair Ritchie");
        final Author ANDREW_TANENBAUM = new Author("Andrew Stuart Tanenbaum");
        dao.insert(RICHARD_STALLMAN);
        dao.insert(DENNIS_RITCHIE);
        dao.insert(ANDREW_TANENBAUM);
        final List<Author> authors = dao.getAll();
        assertTrue(authors.containsAll(Arrays.asList(ANDREW_TANENBAUM, DENNIS_RITCHIE, RICHARD_STALLMAN)));
        dao.delete(RICHARD_STALLMAN);
        dao.delete(DENNIS_RITCHIE);
        dao.delete(ANDREW_TANENBAUM);
    }
}
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
import space.efremov.otusspringlibrary.domain.Tag;
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
public class TagJpaDaoTest {

    @Autowired
    private TagJpaDao dao;

    @Test
    public void count() {
        final Tag PYTHON = new Tag("Python");
        assertEquals(dao.count().longValue(), 0);
        dao.insert(PYTHON);
        assertEquals(dao.count().longValue(), 1);
        dao.delete(PYTHON);
    }

    @Test
    public void insert() {
        final Tag JAVA = new Tag("Java");
        dao.insert(JAVA);
        assertTrue(dao.getAll().contains(JAVA));
        assertEquals(dao.count().longValue(), 1);
        dao.delete(JAVA);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdAndDelete() {
        final Tag SPRING = new Tag("Spring");
        dao.insert(SPRING);
        final Tag tag = dao.getById(SPRING.getId());
        assertEquals(tag, SPRING);
        dao.delete(SPRING);
        dao.getById(SPRING.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdNotFoundTest() {
        dao.getById(1L);
    }

    @Test
    public void getAll() {
        final Tag PYTHON = new Tag("Python");
        final Tag JAVA = new Tag("Java");
        final Tag SPRING = new Tag("Spring");
        dao.insert(PYTHON);
        dao.insert(JAVA);
        dao.insert(SPRING);
        final List<Tag> authors = dao.getAll();
        assertTrue(authors.containsAll(Arrays.asList(SPRING, JAVA, PYTHON)));
        dao.delete(PYTHON);
        dao.delete(JAVA);
        dao.delete(SPRING);
    }
}
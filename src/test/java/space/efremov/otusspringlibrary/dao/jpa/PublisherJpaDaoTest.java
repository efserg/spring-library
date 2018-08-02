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
import space.efremov.otusspringlibrary.domain.Publisher;
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
public class PublisherJpaDaoTest {

    @Autowired
    private PublisherJpaDao dao;

    @Test
    public void count() {
        final Publisher OREILLY = new Publisher("O'Reilly Media");
        assertEquals(dao.count().longValue(), 0);
        dao.insert(OREILLY);
        assertEquals(dao.count().longValue(), 1);
        dao.delete(OREILLY);
    }

    @Test
    public void insert() {
        final Publisher ADDISON = new Publisher("Addison-Wesley");
        dao.insert(ADDISON);
        assertTrue(dao.getAll().contains(ADDISON));
        assertEquals(dao.count().longValue(), 1);
        dao.delete(ADDISON);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdAndDelete() {
        final Publisher MS = new Publisher("Microsoft Press");
        dao.insert(MS);
        final Publisher publisher = dao.getById(MS.getId());
        assertEquals(publisher, MS);
        dao.delete(MS);
        dao.getById(MS.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdNotFoundTest() {
        dao.getById(1L);
    }

    @Test
    public void getAll() {
        final Publisher OREILLY = new Publisher("O'Reilly Media");
        final Publisher ADDISON = new Publisher("Addison-Wesley");
        final Publisher MS = new Publisher("Microsoft Press");
        dao.insert(OREILLY);
        dao.insert(ADDISON);
        dao.insert(MS);
        final List<Publisher> authors = dao.getAll();
        assertTrue(authors.containsAll(Arrays.asList(MS, ADDISON, OREILLY)));
        dao.delete(OREILLY);
        dao.delete(ADDISON);
        dao.delete(MS);
    }
}
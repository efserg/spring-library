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
import space.efremov.otusspringlibrary.domain.Person;
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
public class PersonJpaDaoTest {

    @Autowired
    private PersonJpaDao dao;

    @Test
    public void count() {
        final Person PUPKIN = new Person("vasiliy_pupkin@mail.ru", "vaspup");
        assertEquals(dao.count().longValue(), 0);
        dao.insert(PUPKIN);
        assertEquals(dao.count().longValue(), 1);
        dao.delete(PUPKIN);
    }

    @Test
    public void insert() {
        final Person PUPKIN = new Person("vasiliy_pupkin@mail.ru", "vaspup");
        dao.insert(PUPKIN);
        assertTrue(dao.getAll().contains(PUPKIN));
        assertEquals(dao.count().longValue(), 1);
        dao.delete(PUPKIN);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdAndDelete() {
        final Person PUPKIN = new Person("vasiliy_pupkin@mail.ru", "vaspup");
        dao.insert(PUPKIN);
        final Person person = dao.getById(PUPKIN.getId());
        assertEquals(person, PUPKIN);
        dao.delete(PUPKIN);
        dao.getById(PUPKIN.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdNotFoundTest() {
        dao.getById(1L);
    }

    @Test
    public void getAll() {
        final Person PUPKIN = new Person("vasiliy_pupkin@mail.ru", "vaspup");
        final Person JOHN_DOE = new Person("john.doe@akme.com", "john");
        final Person JUAN = new Person("juan@mail.com", "Juan Perez");
        dao.insert(PUPKIN);
        dao.insert(JOHN_DOE);
        dao.insert(JUAN);
        final List<Person> people = dao.getAll();
        assertTrue(people.containsAll(Arrays.asList(JUAN, JOHN_DOE, PUPKIN)));
        dao.delete(PUPKIN);
        dao.delete(JOHN_DOE);
        dao.delete(JUAN);
    }
}
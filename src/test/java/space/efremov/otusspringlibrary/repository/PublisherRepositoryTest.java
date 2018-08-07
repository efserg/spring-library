package space.efremov.otusspringlibrary.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Publisher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PublisherRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private PublisherRepository repository;

    @Test
    public void savePublisherTest() {
        final Publisher APRESS = new Publisher("Apress");
        final Publisher publisher = repository.save(APRESS);
        final Publisher find = em.find(Publisher.class, publisher.getId());
        assertThat(find).isEqualTo(publisher);
    }

    @Test
    public void getPublisherByIdTest() {
        final Publisher MS = new Publisher("Microsoft press");
        final Long msId = em.persistAndGetId(MS, Long.class);
        final Optional<Publisher> publisher = repository.findById(msId);
        assertThat(publisher).contains(MS);
    }

    @Test
    public void getNotExistPublisherByIdTest() {
        final long NOT_EXIST_PUBLISHER_ID = 1001L;
        final Optional<Publisher> publisher = repository.findById(NOT_EXIST_PUBLISHER_ID);
        assertThat(publisher).isEmpty();
    }

    @Test
    public void findPublisherAllTest() {
        final Publisher APRESS = new Publisher("Apress");
        final Publisher WILLEY = new Publisher("John Wiley & Sons");
        final Publisher SAMS = new Publisher("Sams Publishing");
        em.persist(APRESS);
        em.persist(WILLEY);
        em.persist(SAMS);
        final List<Publisher> publishers = repository.findAll();
        assertThat(publishers).containsOnly(APRESS, WILLEY, SAMS);
    }

    @Test
    public void deletePublisherTest() {
        final Publisher PRAGMATIC = new Publisher("The Pragmatic Programmer");
        final Long pragmaticId = em.persistAndGetId(PRAGMATIC, Long.class);
        final Optional<Publisher> publisher = repository.findById(pragmaticId);
        assertThat(publisher).isNotEmpty();
        repository.delete(publisher.get());
        final Optional<Publisher> removed = repository.findById(pragmaticId);
        assertThat(removed).isEmpty();
    }

}
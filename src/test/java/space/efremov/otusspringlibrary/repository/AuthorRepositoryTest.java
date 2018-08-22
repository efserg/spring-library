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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository repository;

    @Test
    public void saveAuthorTest() {
        final Author brian = new Author("Brian Wilson Kernighan");
        final Author author = repository.save(brian);
        final Author find = em.find(Author.class, author.getId());
        assertThat(find).isEqualTo(author);
    }

    @Test
    public void getAuthorByIdTest() {
        final Author richardStallman = new Author("Richard Matthew Stallman");
        final Long stallmanId = em.persistAndGetId(richardStallman, Long.class);
        final Optional<Author> author = repository.findById(stallmanId);
        assertThat(author).contains(richardStallman);
    }

    @Test
    public void getNotExistAuthorByIdTest() {
        final long notExistAuthorId = 1001L;
        final Optional<Author> author = repository.findById(notExistAuthorId);
        assertThat(author).isEmpty();
    }

    @Test
    public void findAuthorAllTest() {
        repository.deleteAll();
        final Author richardStallman = new Author("Richard Matthew Stallman");
        final Author dennisRitchie = new Author("Dennis MacAlistair Ritchie");
        final Author andrewTanenbaum = new Author("Andrew Stuart Tanenbaum");
        em.persist(richardStallman);
        em.persist(dennisRitchie);
        em.persist(andrewTanenbaum);
        final List<Author> authors = repository.findAll();
        assertThat(authors).containsOnly(richardStallman, dennisRitchie, andrewTanenbaum);
    }

    @Test
    public void deleteAuthorTest() {
        final Author dennisRitchie = new Author("Dennis MacAlistair Ritchie");
        final Long ritchieId = em.persistAndGetId(dennisRitchie, Long.class);
        final Optional<Author> author = repository.findById(ritchieId);
        assertThat(author).isNotEmpty();
        repository.delete(author.get());
        final Optional<Author> removed = repository.findById(ritchieId);
        assertThat(removed).isEmpty();
    }

}
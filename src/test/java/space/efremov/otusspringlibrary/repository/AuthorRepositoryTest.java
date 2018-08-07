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
        final Author BRIAN = new Author("Brian Wilson Kernighan");
        final Author author = repository.save(BRIAN);
        final Author find = em.find(Author.class, author.getId());
        assertThat(find).isEqualTo(author);
    }

    @Test
    public void getAuthorByIdTest() {
        final Author RICHARD_STALLMAN = new Author("Richard Matthew Stallman");
        final Long stallmanId = em.persistAndGetId(RICHARD_STALLMAN, Long.class);
        final Optional<Author> author = repository.findById(stallmanId);
        assertThat(author).contains(RICHARD_STALLMAN);
    }

    @Test
    public void getNotExistAuthorByIdTest() {
        final long NOT_EXIST_AUTHOR_ID = 1001L;
        final Optional<Author> author = repository.findById(NOT_EXIST_AUTHOR_ID);
        assertThat(author).isEmpty();
    }

    @Test
    public void findAuthorAllTest() {
        final Author RICHARD_STALLMAN = new Author("Richard Matthew Stallman");
        final Author DENNIS_RITCHIE = new Author("Dennis MacAlistair Ritchie");
        final Author ANDREW_TANENBAUM = new Author("Andrew Stuart Tanenbaum");
        em.persist(RICHARD_STALLMAN);
        em.persist(DENNIS_RITCHIE);
        em.persist(ANDREW_TANENBAUM);
        final List<Author> authors = repository.findAll();
        assertThat(authors).containsOnly(RICHARD_STALLMAN, DENNIS_RITCHIE, ANDREW_TANENBAUM);
    }

    @Test
    public void deleteAuthorTest() {
        final Author DENNIS_RITCHIE = new Author("Dennis MacAlistair Ritchie");
        final Long ritchieId = em.persistAndGetId(DENNIS_RITCHIE, Long.class);
        final Optional<Author> author = repository.findById(ritchieId);
        assertThat(author).isNotEmpty();
        repository.delete(author.get());
        final Optional<Author> removed = repository.findById(ritchieId);
        assertThat(removed).isEmpty();
    }

}
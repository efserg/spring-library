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
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.domain.Tag;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository repository;

    @Test
    public void getBookByIdTest() {
        final Author dennis = new Author("Dennis MacAlistair Ritchie");
        final Author brian = new Author("Brian Wilson Kernighan");
        final Publisher bellLabs = new Publisher("AT&T Bell Labs");
        final Tag c = new Tag("C");
        final Tag classic = new Tag("Classic");
        final Book the_c_programming_language = new Book("The C Programming Language", "0-13-110163-3", 1978, bellLabs, Arrays.asList(c, classic), Arrays.asList(dennis, brian));
        final Long cId = em.persistAndGetId(the_c_programming_language, Long.class);
        final Optional<Book> book = repository.findById(cId);
        assertThat(book).contains(the_c_programming_language);
    }

    @Test
    public void getNotExistBookByIdTest() {
        final long notExistBookId = 1001L;
        final Optional<Book> book = repository.findById(notExistBookId);
        assertThat(book).isEmpty();
    }

    @Test
    public void deleteBookTest() {
        final Author dennis = new Author("Dennis MacAlistair Ritchie");
        final Author brian = new Author("Brian Wilson Kernighan");
        final Publisher bellLabs = new Publisher("AT&T Bell Labs");
        final Tag C = new Tag("C");
        final Tag classic = new Tag("Classic");
        final Book cLanguage = new Book("The C Programming Language", "0-13-110163-3", 1978, bellLabs, Arrays.asList(C, classic), Arrays.asList(dennis, brian));
        final Long cId = em.persistAndGetId(cLanguage, Long.class);
        final Optional<Book> book = repository.findById(cId);
        assertThat(book).isNotEmpty();
        repository.delete(book.get());
        final Optional<Book> removed = repository.findById(cId);
        assertThat(removed).isEmpty();
    }

}
package space.efremov.otusspringlibrary.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.domain.*;
import space.efremov.otusspringlibrary.domain.Book;

import java.util.Arrays;
import java.util.List;
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
        final Author DENNIS = new Author("Dennis MacAlistair Ritchie");
        final Author BRIAN = new Author("Brian Wilson Kernighan");
        final Publisher BELL_LABS = new Publisher("AT&T Bell Labs");
        final Tag C = new Tag("C");
        final Tag CLASSIC = new Tag("Classic");
        final Book C_LANGUAGE = new Book("The C Programming Language", "0-13-110163-3", 1978, BELL_LABS, Arrays.asList(C, CLASSIC), Arrays.asList(DENNIS, BRIAN));
        final Long cId = em.persistAndGetId(C_LANGUAGE, Long.class);
        final Optional<Book> book = repository.findById(cId);
        assertThat(book).contains(C_LANGUAGE);
    }

    @Test
    public void getNotExistBookByIdTest() {
        final long NOT_EXIST_BOOK_ID = 1001L;
        final Optional<Book> book = repository.findById(NOT_EXIST_BOOK_ID);
        assertThat(book).isEmpty();
    }

    @Test
    public void deleteBookTest() {
        final Author DENNIS = new Author("Dennis MacAlistair Ritchie");
        final Author BRIAN = new Author("Brian Wilson Kernighan");
        final Publisher BELL_LABS = new Publisher("AT&T Bell Labs");
        final Tag C = new Tag("C");
        final Tag CLASSIC = new Tag("Classic");
        final Book C_LANGUAGE = new Book("The C Programming Language", "0-13-110163-3", 1978, BELL_LABS, Arrays.asList(C, CLASSIC), Arrays.asList(DENNIS, BRIAN));
        final Long cId = em.persistAndGetId(C_LANGUAGE, Long.class);
        final Optional<Book> book = repository.findById(cId);
        assertThat(book).isNotEmpty();
        repository.delete(book.get());
        final Optional<Book> removed = repository.findById(cId);
        assertThat(removed).isEmpty();
    }

}
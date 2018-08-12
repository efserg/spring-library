package space.efremov.otusspringlibrary.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.domain.User;
import space.efremov.otusspringlibrary.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository repository;

    @Test
    public void getUserByIdTest() {
        final User VASILIY_PUPKIN = new User("vasya.pupkin@mail.ru", "vasya_pupkin");
        final Long pupkinId = em.persistAndGetId(VASILIY_PUPKIN, Long.class);
        final Optional<User> user = repository.findById(pupkinId);
        assertThat(user).contains(VASILIY_PUPKIN);
    }

    @Test
    public void getNotExistUserByIdTest() {
        final long NOT_EXIST_USER_ID = 1001L;
        final Optional<User> user = repository.findById(NOT_EXIST_USER_ID);
        assertThat(user).isEmpty();
    }

    @Test
    public void findUserAllTest() {
        final User NORMALVERBRAUCHER = new User("normalverbraucher@company.de", "Otto.Normalverbraucher");
        final User SVENSSON = new User("medelsvensson@company.sw", "Medelsvensson");
        final User ROSSY = new User("mario_rossi@company.it", "MarioRossi");
        em.persist(NORMALVERBRAUCHER);
        em.persist(SVENSSON);
        em.persist(ROSSY);
        final List<User> users = repository.findAll();
        assertThat(users).containsOnly(NORMALVERBRAUCHER, SVENSSON, ROSSY);
    }

    @Test
    public void deleteUserTest() {
        final User JOE = new User("joe_bloggs@company.uk", "JoE_BlOgGs");
        final Long joeId = em.persistAndGetId(JOE, Long.class);
        final Optional<User> user = repository.findById(joeId);
        assertThat(user).isNotEmpty();
        repository.delete(user.get());
        final Optional<User> removed = repository.findById(joeId);
        assertThat(removed).isEmpty();
    }

}
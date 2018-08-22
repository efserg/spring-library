package space.efremov.otusspringlibrary.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.domain.Tag;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TagRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private TagRepository repository;

    @Test
    public void saveTagTest() {
        final Tag kotlin = new Tag("Kotlin");
        final Tag tag = repository.save(kotlin);
        final Tag find = em.find(Tag.class, tag.getId());
        assertThat(find).isEqualTo(tag);
    }

    @Test
    public void getTagByIdTest() {
        final Tag python = new Tag("Python");
        final Long pythonId = em.persistAndGetId(python, Long.class);
        final Optional<Tag> tag = repository.findById(pythonId);
        assertThat(tag).contains(python);
    }

    @Test
    public void getNotExistTagByIdTest() {
        final long notExistTagId = 1001L;
        final Optional<Tag> tag = repository.findById(notExistTagId);
        assertThat(tag).isEmpty();
    }

    @Test
    public void findTagAllTest() {
        repository.deleteAll();
        final Tag java = new Tag("Java");
        final Tag machineLearning = new Tag("Machine learning");
        final Tag OS = new Tag("OS");
        em.persist(java);
        em.persist(machineLearning);
        em.persist(OS);
        final List<Tag> tags = repository.findAll();
        assertThat(tags).containsOnly(java, machineLearning, OS);
    }

    @Test
    public void deleteTagTest() {
        final Tag spring = new Tag("Spring");
        final Long ritchieId = em.persistAndGetId(spring, Long.class);
        final Optional<Tag> tag = repository.findById(ritchieId);
        assertThat(tag).isNotEmpty();
        repository.delete(tag.get());
        final Optional<Tag> removed = repository.findById(ritchieId);
        assertThat(removed).isEmpty();
    }

}
package space.efremov.otusspringlibrary.controller.impl.console;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import space.efremov.otusspringlibrary.dao.TestConfig;
import space.efremov.otusspringlibrary.dao.jpa.*;
import space.efremov.otusspringlibrary.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class,
        properties = {
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ReviewConsoleControllerTest {

    @Autowired
    private ReviewConsoleController controller;

    @Autowired
    private BookJpaDao bookJpaDao;
    @Autowired
    private UserJpaDao userJpaDao;
    @Autowired
    private AuthorJpaDao authorJpaDao;
    @Autowired
    private PublisherJpaDao publisherJpaDao;
    @Autowired
    private TagJpaDao tagJpaDao;

    private Long bookId;

    private Long userId;

    @Before
    public void fillDB() {
        final Author ANDREW_TANENBAUM = new Author("Andrew Stuart Tanenbaum");
        final Publisher PAPERBACK = new Publisher("Paperback");
        final Tag os = new Tag("OS");
        final Tag classical_book = new Tag("Classical book");
        tagJpaDao.insert(os);
        tagJpaDao.insert(classical_book);
        final List<Tag> TAGS = Arrays.asList(os, classical_book);
        authorJpaDao.insert(ANDREW_TANENBAUM);
        publisherJpaDao.insert(PAPERBACK);
        final Book modern_operating_systems = new Book("Modern Operating Systems", "9789332575776", 2016, PAPERBACK, TAGS, Collections.singletonList(ANDREW_TANENBAUM));
        final User agent_smith = new User("agent.smith@matrix.com", "john-smith");
        userJpaDao.insert(agent_smith);
        bookJpaDao.insert(modern_operating_systems);
        bookId = modern_operating_systems.getId();
        userId = agent_smith.getId();
    }

    @Test
    public void addReview() {
        controller.addReview(userId, bookId, "I will kill you, Mr. Anderson");
    }
}
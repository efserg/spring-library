package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.dao.jpa.AuthorJpaDao;
import space.efremov.otusspringlibrary.dao.jpa.PersonJpaDao;
import space.efremov.otusspringlibrary.dao.jpa.PublisherJpaDao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;

import static space.efremov.otusspringlibrary.controller.impl.console.AppConstant.ENTITY_NOT_FOUND_ERROR;

@ShellComponent
@ShellCommandGroup("publisher")
public class PublisherConsoleController {

    private final PublisherJpaDao dao;

    public PublisherConsoleController(PublisherJpaDao dao) {
        this.dao = dao;
    }

    @ShellMethod(value = "Add publisher to DB.", key = "publisher add")
    public Publisher add(@ShellOption(help = "Publisher name. Use quotes if you need complex name, e.g. \"O'Reilly Media\"") String name) {
        return dao.insert(new Publisher(name));
    }

    @ShellMethod(value = "Remove publisher from DB.", key = "publisher remove")
    public String remove(@ShellOption(help = "Publisher ID. You can use \"publisher list\" command to found ID") Long id) {
        try {
            final Publisher publisher = dao.getById(id);
            dao.delete(publisher);
            return "Success";
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get publisher from DB.", key = "publisher get")
    public String get(@ShellOption(help = "Publisher ID.") Long id) {
        try {
            return dao.getById(id).toString();
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get all publishers from DB.", key = "publisher list")
    public List<Publisher> list() {
        return dao.getAll();
    }
}

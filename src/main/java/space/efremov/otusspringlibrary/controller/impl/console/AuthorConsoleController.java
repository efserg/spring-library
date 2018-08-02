package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.dao.jpa.AuthorJpaDao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;

@ShellComponent
@ShellCommandGroup("author")
public class AuthorConsoleController {

    private final AuthorJpaDao dao;

    public AuthorConsoleController(AuthorJpaDao dao) {
        this.dao = dao;
    }

    @ShellMethod(value = "Add author to DB.", key = "author add")
    public Author add(@ShellOption(help = "Author name. Use quotes if you need first name and last name, e.g. \"John Smith jr.\"") String name) {
        return dao.insert(new Author(name));
    }

    @ShellMethod(value = "Remove author from DB.", key = "author remove")
    public void remove(@ShellOption(help = "Author ID. You can use \"author list\" command to found ID") Long id) {
        try {
            final Author author = dao.getById(id);
            dao.delete(author);
        } catch (EntityNotFoundException e) {
            return;
        }
    }

    @ShellMethod(value = "Get author from DB.", key = "author get")
    public Author get(@ShellOption(help = "Author ID.") Long id) {
        return dao.getById(id);
    }

    @ShellMethod(value = "Get all authors from DB.", key = "author list")
    public List<Author> list() {
        return dao.getAll();

    }
}

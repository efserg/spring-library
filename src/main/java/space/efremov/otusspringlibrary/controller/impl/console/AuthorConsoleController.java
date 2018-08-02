package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.dao.jpa.AuthorJpaDao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;

import static space.efremov.otusspringlibrary.controller.impl.console.AppConstant.ENTITY_NOT_FOUND_ERROR;

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
    public String remove(@ShellOption(help = "Author ID. You can use \"author list\" command to found ID") Long id) {
        try {
            final Author author = dao.getById(id);
            dao.delete(author);
            return "Success";
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get author from DB.", key = "author get")
    public String get(@ShellOption(help = "Author ID.") Long id) {
        try {
            return dao.getById(id).toString();
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get all authors from DB.", key = "author list")
    public List<Author> list() {
        return dao.getAll();
    }
}

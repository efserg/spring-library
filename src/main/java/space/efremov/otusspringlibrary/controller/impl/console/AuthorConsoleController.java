package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.controller.AuthorController;
import space.efremov.otusspringlibrary.dao.jdbc.AuthorDao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.service.IdGenerator;

import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("author")
public class AuthorConsoleController implements AuthorController {

    private final AuthorDao dao;
    private final IdGenerator idGenerator;

    public AuthorConsoleController(AuthorDao dao, IdGenerator idGenerator) {
        this.dao = dao;
        this.idGenerator = idGenerator;
    }

    @Override
    @ShellMethod(value = "Add author to DB.", key = "author-add")
    public Author add(@ShellOption(help = "Author name. Use quotes if you need first name and last name, e.g. \"John Smith jr.\"") String name) {
        final Author entity = new Author(idGenerator.next(), name);
        return dao.insert(entity);
    }

    @Override
    @ShellMethod(value = "Remove author from DB.", key = "author-remove")
    public void remove(@ShellOption(help = "Author ID. You can use \"author-get\" command without id param to found ID") int id) {
        dao.delete(dao.getById(id));
    }

    @Override
    @ShellMethod(value = "Get author(s) from DB.", key = "author-get")
    public String get(@ShellOption(help = "Author ID.", defaultValue = "-1") Integer id) {
        if (id == -1) {
            return dao.getAll().stream().map(a -> String.format("%s %s\n", a.getId(), a.getName())).collect(Collectors.joining());
        } else {
            return dao.getById(id).toString();
        }
    }
}

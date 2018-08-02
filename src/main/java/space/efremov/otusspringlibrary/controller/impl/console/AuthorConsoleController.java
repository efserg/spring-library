package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.controller.AuthorController;
import space.efremov.otusspringlibrary.dao.jpa.AuthorJpaDao;
import space.efremov.otusspringlibrary.domain.Author;

@ShellComponent
@ShellCommandGroup("author")
public class AuthorConsoleController implements AuthorController {

    private final AuthorJpaDao dao;

    public AuthorConsoleController(AuthorJpaDao dao) {
        this.dao = dao;
    }

    @Override
    @ShellMethod(value = "Add author to DB.", key = "author-add")
    public Author add(@ShellOption(help = "Author name. Use quotes if you need first name and last name, e.g. \"John Smith jr.\"") String name) {
        return dao.insert(new Author(name));
    }

    @Override
    @ShellMethod(value = "Remove author from DB.", key = "author-remove")
    public void remove(@ShellOption(help = "Author ID. You can use \"author-get\" command without id param to found ID") Long id) {


    }

    @Override
    @ShellMethod(value = "Get author(s) from DB.", key = "author-get")
    public String get(@ShellOption(help = "Author ID.", defaultValue = "-1") Long id) {
        return null;
    }
}

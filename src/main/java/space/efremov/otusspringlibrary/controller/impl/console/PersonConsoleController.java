package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.dao.jpa.UserJpaDao;
import space.efremov.otusspringlibrary.domain.User;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;

import static space.efremov.otusspringlibrary.controller.impl.console.AppConstant.ENTITY_NOT_FOUND_ERROR;

@ShellComponent
@ShellCommandGroup("user")
public class PersonConsoleController {

    private final UserJpaDao dao;

    public PersonConsoleController(UserJpaDao dao) {
        this.dao = dao;
    }

    @ShellMethod(value = "Add user to DB.", key = "user add")
    public User add(@ShellOption(help = "user username. Use quotes if you need first name and last name, e.g. \"John Smith jr.\"") String username, @ShellOption(help = "user email") String email) {
        return dao.insert(new User(email, username));
    }

    @ShellMethod(value = "Remove user from DB.", key = "user remove")
    public String remove(@ShellOption(help = "user ID. You can use \"user list\" command to found ID") Long id) {
        try {
            final User user = dao.getById(id);
            dao.delete(user);
            return "Success";
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get user from DB.", key = "user get")
    public String get(@ShellOption(help = "user ID.") Long id) {
        try {
            return dao.getById(id).toString();
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get all users from DB.", key = "user list")
    public List<User> list() {
        return dao.getAll();
    }
}

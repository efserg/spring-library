package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.otusspringlibrary.domain.User;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.UserRepository;

import java.util.List;

@ShellComponent
@ShellCommandGroup("user")
@Transactional(readOnly = true)
public class UserConsoleController {

    private final UserRepository userRepository;

    public UserConsoleController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ShellMethod(value = "Add user to DB.", key = {"user add", "user-add"})
    @Transactional
    public User add(@ShellOption(help = "User username. Use quotes if you need first name and last name, e.g. \"John Smith jr.\"", value = {"username", "name"}) String username,
                    @ShellOption(help = "User email") String email) {
        return userRepository.save(new User(email, username));
    }

    @ShellMethod(value = "Remove user from DB.", key = {"user remove", "user-remove"})
    @Transactional
    public void remove(@ShellOption(help = "user ID. You can use \"user list\" command to found ID", value = {"user-id", "uid", "userId", "id"}) Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @ShellMethod(value = "Get user from DB.", key = {"user get", "user-get"})
    public User get(@ShellOption(help = "User ID.", value = {"user-id", "uid", "userId", "id"}) Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ShellMethod(value = "Get all users from DB.", key = {"user list", "user-list"})
    public List<User> list() {
        return userRepository.findAll();
    }
}

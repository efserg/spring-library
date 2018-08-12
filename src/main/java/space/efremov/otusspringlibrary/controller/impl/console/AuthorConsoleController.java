package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@ShellComponent
@ShellCommandGroup("author")
@Transactional(readOnly = true)
public class AuthorConsoleController {

    private final AuthorRepository authorRepository;

    public AuthorConsoleController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @ShellMethod(value = "Add author to DB.", key = {"author add", "author-add"})
    @Transactional
    public Author add(@ShellOption(help = "Author name. Use quotes if you need first name and last name, e.g. \"John Smith jr.\"", value = {"author", "author-name", "name", "authorName"}) String name) {
        return authorRepository.save(new Author(name));
    }

    @ShellMethod(value = "Remove author from DB.", key = {"author remove", "author-remove"})
    @Transactional
    public void remove(@ShellOption(help = "Author ID. You can use \"author list\" command to found ID", value = {"author-id", "aid", "authorId", "id"}) Long id) {
        final Optional<Author> author = authorRepository.findById(id);
        authorRepository.delete(author.orElseThrow(EntityNotFoundException::new));

    }

    @ShellMethod(value = "Get author from DB.", key = {"author get", "author-get"})
    public Author get(@ShellOption(help = "Author ID.", value = {"author-id", "aid", "authorId", "id"}) Long id) {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ShellMethod(value = "Get all authors from DB.", key = {"author list", "author-list"})
    public List<Author> list() {
        return authorRepository.findAll();
    }
}

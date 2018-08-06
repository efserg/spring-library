package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.PublisherRepository;

import java.util.List;

@ShellComponent
@ShellCommandGroup("publisher")
@Transactional(readOnly = true)
public class PublisherConsoleController {

    private final PublisherRepository publisherRepository;

    public PublisherConsoleController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @ShellMethod(value = "Add publisher to DB.", key = {"publisher add", "publisher-add"})
    @Transactional
    public Publisher add(@ShellOption(help = "Publisher name. Use quotes if you need complex name, e.g. \"O'Reilly Media\"", value = {"publisher-name", "name", "publisher", "publisherName"}) String name) {
        return publisherRepository.save(new Publisher(name));
    }

    @ShellMethod(value = "Remove publisher from DB.", key = {"publisher remove", "publisher-remove"})
    @Transactional
    public void remove(@ShellOption(help = "Publisher ID. You can use \"publisher list\" command to found ID", value = {"publisher-id", "pid", "publisherId", "id"}) Long id) {
        publisherRepository.delete(publisherRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @ShellMethod(value = "Get publisher from DB.", key = {"publisher get", "publisher-get"})
    public Publisher get(@ShellOption(help = "Publisher ID.", value = {"publisher-id", "pid", "publisherId", "id"}) Long id) {
        return publisherRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ShellMethod(value = "Get all publishers from DB.", key = {"publisher list", "publisher-list"})
    public List<Publisher> list() {
        return publisherRepository.findAll();
    }
}

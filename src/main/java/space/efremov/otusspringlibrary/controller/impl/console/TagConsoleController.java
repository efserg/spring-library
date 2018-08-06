package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.TagRepository;

import java.util.List;

@ShellComponent
@ShellCommandGroup("tag")
@Transactional(readOnly = true)
public class TagConsoleController {

    private final TagRepository tagRepository;

    public TagConsoleController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @ShellMethod(value = "Add tag to DB.", key = {"tag add", "tag-add"})
    @Transactional
    public Tag add(@ShellOption(help = "Tag name. Use quotes if you need complex tag name, e.g. \"Machine learning\"", value = {"tag-name", "name", "tag", "tagName"}) String name) {
        return tagRepository.save(new Tag(name));
    }

    @ShellMethod(value = "Remove tag from DB.", key = {"tag remove", "tag-remove"})
    @Transactional
    public void remove(@ShellOption(help = "Tag ID. You can use \"tag list\" command to found ID", value = {"tag-id", "tid", "tagId", "id"}) Long id) {
        tagRepository.delete(tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @ShellMethod(value = "Get tag from DB.", key = {"tag get", "tag-get"})
    public Tag get(@ShellOption(help = "Tag ID.", value = {"tag-id", "tid", "tagId", "id"}) Long id) {
        return tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ShellMethod(value = "Get all tags from DB.", key = {"tag list", "tag-list"})
    public List<Tag> list() {
        return tagRepository.findAll();
    }
}

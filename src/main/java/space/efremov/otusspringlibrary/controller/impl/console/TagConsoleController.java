package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.dao.jpa.AuthorJpaDao;
import space.efremov.otusspringlibrary.dao.jpa.TagJpaDao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;

import static space.efremov.otusspringlibrary.controller.impl.console.AppConstant.ENTITY_NOT_FOUND_ERROR;

@ShellComponent
@ShellCommandGroup("tag")
public class TagConsoleController {

    private final TagJpaDao dao;

    public TagConsoleController(TagJpaDao dao) {
        this.dao = dao;
    }

    @ShellMethod(value = "Add tag to DB.", key = "tag add")
    public Tag add(@ShellOption(help = "Tag name. Use quotes if you need complex tag name, e.g. \"Machine learning\"") String name) {
        return dao.insert(new Tag(name));
    }

    @ShellMethod(value = "Remove tag from DB.", key = "tag remove")
    public String remove(@ShellOption(help = "Tag ID. You can use \"tag list\" command to found ID") Long id) {
        try {
            final Tag tag = dao.getById(id);
            dao.delete(tag);
            return "Success";
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get tag from DB.", key = "tag get")
    public String get(@ShellOption(help = "Tag ID.") Long id) {
        try {
            return dao.getById(id).toString();
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get all tags from DB.", key = "tag list")
    public List<Tag> list() {
        return dao.getAll();
    }
}

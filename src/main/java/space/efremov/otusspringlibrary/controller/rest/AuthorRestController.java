package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    ModelAndView all() {
        final List<Author> authors = this.authorRepository.findAll();
        ModelAndView model = new ModelAndView("author/list");
        model.addObject("authors", authors);
        return model;
    }

    @GetMapping(value = "{id}")
    ModelAndView author(@PathVariable("id") long id) {
        final Author author = findAuthorById(id);
        ModelAndView model = new ModelAndView("author/detail");
        model.addObject("author", author);
        model.addObject("books", author.getBooks());
        return model;
    }

    @GetMapping(value = "add")
    ModelAndView create() {
        final Author author = new Author("");
        authorRepository.save(author);
        ModelAndView model = new ModelAndView("author/detail");
        model.addObject("author", author);
        return model;
    }

    @PostMapping(path = "{id}/edit")
    ModelAndView update(Author input, @PathParam("id") long id) {
        final Author author = findAuthorById(id);
        author.setName(input.getName());
        authorRepository.save(author);
        ModelAndView mav = new ModelAndView("redirect:/authors");
        mav.addObject("authors", authorRepository.findAll());
        return mav;
    }

    @DeleteMapping(value = "{id}")
    ModelAndView delete(@PathVariable("id") long id) {
        final Author author = findAuthorById(id);
        authorRepository.delete(author);
        return new ModelAndView("redirect:/authors");
    }

    private Author findAuthorById(long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        return author.orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }

}

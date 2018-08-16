package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;

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
        ModelAndView model = new ModelAndView("authorsView");
        model.addObject("authors", authors);
        return model;
    }

    @GetMapping(value = "{id}")
    ModelAndView author(@PathVariable("id") long id) {
        final Author author = findAuthorById(id);
        ModelAndView model = new ModelAndView("authorView");
        model.addObject("author", author);
        return model;
    }

    private Author findAuthorById(long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        return author.orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }


}

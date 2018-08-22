package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;
import space.efremov.otusspringlibrary.repository.BookRepository;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorRestController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorRestController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    ModelAndView list(@RequestParam(required = false, name = "bookId") Long bookId) {
        final List<Author> authors;
        if (bookId == null) {
            authors = authorRepository.findAll();
        } else {
            final Optional<Book> book = bookRepository.findById(bookId);
            authors = book.map(Book::getAuthors).orElse(Collections.emptyList());
        }
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

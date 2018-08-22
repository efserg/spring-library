package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;
import space.efremov.otusspringlibrary.repository.BookRepository;
import space.efremov.otusspringlibrary.repository.PublisherRepository;
import space.efremov.otusspringlibrary.repository.TagRepository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final BookRepository bookRepository;
    private final TagRepository tagRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public BookRestController(BookRepository bookRepository, TagRepository tagRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.tagRepository = tagRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    ModelAndView list(@RequestParam(required = false, name = "year") Integer year) {
        final List<Book> books;
        if (year == null) {
            books = bookRepository.findAll();
        } else {
            books = bookRepository.findByYear(year);
        }
        ModelAndView model = new ModelAndView("book/list");
        model.addObject("books", books);
        return model;
    }

    @GetMapping(value = "{id}")
    ModelAndView book(@PathVariable("id") long id) {
        final Book book = findBookById(id);
        ModelAndView model = new ModelAndView("book/detail");
        model.addObject("book", book);
        model.addObject("publishers", publisherRepository.findAll());
        model.addObject("tags", tagRepository.findAll());
        model.addObject("authors", authorRepository.findAll());
        return model;
    }

    @GetMapping(value = "add")
    ModelAndView create() {
        final Book book = new Book("", "", 0, null);
        bookRepository.save(book);
        return book(book.getId());
    }

    @PostMapping(path = "{id}/edit")
    ModelAndView update(Book input, @PathParam("id") long id) {
        final Book book = findBookById(id);
        book.setTitle(input.getTitle());
        book.setAuthors(input.getAuthors());
        book.setIsbn(input.getIsbn());
        book.setPublisher(input.getPublisher());
        book.setTags(input.getTags());
        book.setYear(input.getYear());
        bookRepository.save(book);
        ModelAndView mav = new ModelAndView("redirect:/books");
        mav.addObject("books", bookRepository.findAll());
        return mav;
    }

    @DeleteMapping(value = "{id}")
    ModelAndView delete(@PathVariable("id") long id) {
        final Book book = findBookById(id);
        bookRepository.delete(book);
        return new ModelAndView("redirect:/books");
    }

    private Book findBookById(long id) {
        Optional<Book> author = bookRepository.findById(id);
        return author.orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }
}

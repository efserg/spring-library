package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.BookRepository;
import space.efremov.otusspringlibrary.repository.PublisherRepository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publishers")
public class PublisherRestController {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public PublisherRestController(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    ModelAndView list() {
        final List<Publisher> publishers = publisherRepository.findAll();
        ModelAndView model = new ModelAndView("publisher/list");
        model.addObject("publishers", publishers);
        return model;
    }

    @GetMapping(value = "{id}")
    ModelAndView publisher(@PathVariable("id") long id) {
        final Publisher publisher = findPublisherById(id);
        ModelAndView model = new ModelAndView("publisher/detail");
        model.addObject("publisher", publisher);
        model.addObject("books", publisher.getBooks());
        return model;
    }

    @GetMapping(value = "add")
    ModelAndView create() {
        final Publisher publisher = new Publisher("");
        publisherRepository.save(publisher);
        ModelAndView model = new ModelAndView("publisher/detail");
        model.addObject("publisher", publisher);
        return model;
    }

    @PostMapping(path = "{id}/edit")
    ModelAndView update(Author input, @PathParam("id") long id) {
        final Publisher publisher = findPublisherById(id);
        publisher.setName(input.getName());
        publisherRepository.save(publisher);
        ModelAndView mav = new ModelAndView("redirect:/publishers");
        mav.addObject("authors", publisherRepository.findAll());
        return mav;
    }

    @DeleteMapping(value = "{id}")
    ModelAndView delete(@PathVariable("id") long id) {
        final Publisher publisher = findPublisherById(id);
        publisherRepository.delete(publisher);
        return new ModelAndView("redirect:/publishers");
    }

    private Publisher findPublisherById(long id) {
        Optional<Publisher> publisher = this.publisherRepository.findById(id);
        return publisher.orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }

}

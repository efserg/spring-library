package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler.BookResource;
import space.efremov.otusspringlibrary.controller.rest.model.NestedContentResource;
import space.efremov.otusspringlibrary.controller.rest.model.PublishInput;
import space.efremov.otusspringlibrary.controller.rest.model.PublisherResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.PublisherResourceAssembler.PublisherResource;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.service.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherRestController {

    private final LibraryService service;

    private final PublisherResourceAssembler publisherResourceAssembler;

    private final BookResourceAssembler bookResourceAssembler;

    public PublisherRestController(LibraryService service, PublisherResourceAssembler publisherResourceAssembler, BookResourceAssembler bookResourceAssembler) {
        this.service = service;
        this.publisherResourceAssembler = publisherResourceAssembler;
        this.bookResourceAssembler = bookResourceAssembler;
    }

    @GetMapping
    NestedContentResource<PublisherResource> all() {
        return new NestedContentResource<>(publisherResourceAssembler.toResources(service.getAllPublishers()));
    }

    @GetMapping(value = "/{id}")
    Resource<Publisher> publisher(@PathVariable("id") long id) {
        return publisherResourceAssembler.toResource(service.findPublisherById(id));
    }

    @GetMapping(value = "/{id}/books")
    NestedContentResource<BookResource> books(@PathVariable("id") long id) {
        final List<Book> books = service.findPublisherById(id).getBooks();
        return new NestedContentResource<>(bookResourceAssembler.toResources(books));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Resource<Publisher> create(@RequestBody PublishInput input) {
        final Publisher publisher = service.createPublisher(input.getName());
        return publisherResourceAssembler.toResource(publisher);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) {
        service.removePublisher(id);
    }

}

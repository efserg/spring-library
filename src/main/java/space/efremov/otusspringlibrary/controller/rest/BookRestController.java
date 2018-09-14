package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.otusspringlibrary.controller.rest.model.BookInput;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler.BookResource;
import space.efremov.otusspringlibrary.controller.rest.model.NestedContentResource;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.service.LibraryService;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final LibraryService service;

    private final BookResourceAssembler bookResourceAssembler;

    public BookRestController(LibraryService service, BookResourceAssembler bookResourceAssembler) {
        this.service = service;
        this.bookResourceAssembler = bookResourceAssembler;
    }

    @GetMapping
    NestedContentResource<BookResource> all() {
        return new NestedContentResource<>(bookResourceAssembler.toResources(service.getAllBooks()));
    }

    @GetMapping(value = "/{id}")
    Resource<Book> book(@PathVariable("id") long id) {
        return bookResourceAssembler.toResource(service.findBookById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Resource<Book> create(@RequestBody BookInput input) {
        final Book book = service.createBook(input.getTitle(), input.getIsbn(), input.getYear(), input.getPublisherId(), input.getAuthorIds(), input.getTagIds());
        return bookResourceAssembler.toResource(book);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) {
        service.removeBook(id);
    }

    @PutMapping(value = "/{id}")
    Resource<Book> update(@PathVariable("id") long id, @RequestBody BookInput input) {
        final Book book = service.updateBook(id, input.getTitle(), input.getIsbn(), input.getYear(), input.getPublisherId(), input.getAuthorIds(), input.getTagIds());
        return bookResourceAssembler.toResource(book);
    }


}

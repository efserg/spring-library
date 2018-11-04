package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.otusspringlibrary.controller.rest.model.*;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler.BookResource;
import space.efremov.otusspringlibrary.controller.rest.model.ReviewResourceAssembler.ReviewResource;
import space.efremov.otusspringlibrary.controller.rest.model.AuthorResourceAssembler.AuthorResource;
import space.efremov.otusspringlibrary.controller.rest.model.TagResourceAssembler.TagResource;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.service.LibraryService;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final LibraryService service;

    private final BookResourceAssembler bookResourceAssembler;

    private final ReviewResourceAssembler reviewResourceAssembler;

    private final AuthorResourceAssembler authorResourceAssembler;

    private final TagResourceAssembler tagResourceAssembler;

    public BookRestController(LibraryService service, BookResourceAssembler bookResourceAssembler, ReviewResourceAssembler reviewResourceAssembler, AuthorResourceAssembler authorResourceAssembler, TagResourceAssembler tagResourceAssembler) {
        this.service = service;
        this.bookResourceAssembler = bookResourceAssembler;
        this.reviewResourceAssembler = reviewResourceAssembler;
        this.authorResourceAssembler = authorResourceAssembler;
        this.tagResourceAssembler = tagResourceAssembler;
    }

    @GetMapping
    NestedContentResource<BookResource> all() {
        return new NestedContentResource<>(bookResourceAssembler.toResources(service.getAllBooks()));
    }

    @GetMapping(value = "/{id}")
    Resource<Book> book(@PathVariable("id") long id) {
        return bookResourceAssembler.toResource(service.findBookById(id));
    }

    @GetMapping(value = "/{id}/reviews")
    NestedContentResource<ReviewResource> reviews(@PathVariable("id") long id) {
        return new NestedContentResource<>(
                reviewResourceAssembler.toResources(service.findBookById(id).getReviews())
        );
    }

    @GetMapping(value = "/{id}/authors")
    NestedContentResource<AuthorResource> authors(@PathVariable("id") long id) {
        return new NestedContentResource<>(
                authorResourceAssembler.toResources(service.findBookById(id).getAuthors())
        );
    }

    @GetMapping(value = "/{id}/tags")
    NestedContentResource<TagResource> tags(@PathVariable("id") long id) {
        return new NestedContentResource<>(
                tagResourceAssembler.toResources(service.findBookById(id).getTags())
        );
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

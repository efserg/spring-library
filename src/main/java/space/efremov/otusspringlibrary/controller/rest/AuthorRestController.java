package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.otusspringlibrary.controller.rest.model.AuthorInput;
import space.efremov.otusspringlibrary.controller.rest.model.AuthorResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.AuthorResourceAssembler.AuthorResource;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler.BookResource;
import space.efremov.otusspringlibrary.controller.rest.model.NestedContentResource;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.service.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorRestController {

    private final AuthorResourceAssembler authorResourceAssembler;

    private final BookResourceAssembler bookResourceAssembler;

    private final LibraryService service;

    public AuthorRestController(AuthorResourceAssembler authorResourceAssembler, BookResourceAssembler bookResourceAssembler, LibraryService service) {
        this.service = service;
        this.authorResourceAssembler = authorResourceAssembler;
        this.bookResourceAssembler = bookResourceAssembler;
    }

    @GetMapping
    NestedContentResource<AuthorResource> all() {
        final List<Author> authors = service.getAllAuthors();
        return new NestedContentResource<>(authorResourceAssembler.toResources(authors));
    }

    @GetMapping(value = "/{id}")
    Resource<Author> author(@PathVariable("id") long id) {
        return authorResourceAssembler.toResource(service.findAuthorById(id));
    }

    @GetMapping(value = "/{id}/books")
    NestedContentResource<BookResource> books(@PathVariable("id") long id) {
        final List<Book> books = service.findAuthorById(id).getBooks();
        return new NestedContentResource<>(bookResourceAssembler.toResources(books));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Resource<Author> create(@RequestBody AuthorInput input) {
        final Author author = service.createAuthor(input.getName());
        return authorResourceAssembler.toResource(author);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) {
        service.removeAuthor(id);
    }

}

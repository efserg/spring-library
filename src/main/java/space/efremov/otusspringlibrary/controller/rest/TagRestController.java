package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler.BookResource;
import space.efremov.otusspringlibrary.controller.rest.model.NestedContentResource;
import space.efremov.otusspringlibrary.controller.rest.model.TagInput;
import space.efremov.otusspringlibrary.controller.rest.model.TagResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.TagResourceAssembler.TagResource;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.service.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagRestController {

    private final BookResourceAssembler bookResourceAssembler;

    private final TagResourceAssembler tagResourceAssembler;

    private final LibraryService service;

    public TagRestController(BookResourceAssembler bookResourceAssembler, TagResourceAssembler tagResourceAssembler, LibraryService service) {
        this.bookResourceAssembler = bookResourceAssembler;
        this.tagResourceAssembler = tagResourceAssembler;
        this.service = service;
    }

    @GetMapping
    NestedContentResource<TagResource> all() {
        return new NestedContentResource<>(
                tagResourceAssembler.toResources(service.getAllTags()));
    }

    @GetMapping(value = "/{id}")
    Resource<Tag> tag(@PathVariable("id") long id) {
        return tagResourceAssembler.toResource(service.findTagById(id));
    }

    @GetMapping(value = "/{id}/books")
    NestedContentResource<BookResource> books(@PathVariable("id") long id) {
        final List<Book> books = service.findTagById(id).getBooks();
        return new NestedContentResource<>(bookResourceAssembler.toResources(books));
    }

    @PutMapping(value = "/{id}/add-book")
    Resource<Tag> create(@PathVariable("id") long id, @RequestParam("bookId") long bookId) {
        final Tag tag = service.findTagById(id);
        // todo
        return tagResourceAssembler.toResource(tag);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Resource<Tag> create(@RequestBody TagInput input) {
        final Tag tag = service.createTag(input.getName());
        return tagResourceAssembler.toResource(tag);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) {
        service.removeTag(id);
    }

}

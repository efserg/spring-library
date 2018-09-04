package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.efremov.otusspringlibrary.controller.rest.model.AuthorInput;
import space.efremov.otusspringlibrary.controller.rest.model.AuthorResourceAssembler;
import space.efremov.otusspringlibrary.controller.rest.model.AuthorResourceAssembler.AuthorResource;
import space.efremov.otusspringlibrary.controller.rest.model.NestedContentResource;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;

import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    private final AuthorResourceAssembler authorResourceAssembler;

    public AuthorRestController(AuthorRepository authorRepository, AuthorResourceAssembler authorResourceAssembler) {
        this.authorRepository = authorRepository;
        this.authorResourceAssembler = authorResourceAssembler;
    }

    @GetMapping
    NestedContentResource<AuthorResource> all() {
        return new NestedContentResource<AuthorResource>(
                this.authorResourceAssembler.toResources(this.authorRepository.findAll()));
    }

    @GetMapping(value = "/{id}")
    Resource<Author> author(@PathVariable("id") long id) {
        return this.authorResourceAssembler.toResource(findAuthorById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Resource<Author> create(@RequestBody AuthorInput input) {
        final Author author = new Author(input.getName());
        this.authorRepository.save(author);
        return this.authorResourceAssembler.toResource(author);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) {
        this.authorRepository.deleteById(id);
    }

    private Author findAuthorById(long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        return author.orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }

}

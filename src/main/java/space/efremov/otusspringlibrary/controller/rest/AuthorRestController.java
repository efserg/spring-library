package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;
import space.efremov.otusspringlibrary.controller.rest.AuthorResourceAssembler.AuthorResource;

import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    private final AuthorResourceAssembler authorResourceAssembler;

    public AuthorRestController(AuthorRepository authorRepository, AuthorResourceAssembler authorResourceAssembler) {
        this.authorRepository = authorRepository;
        this.authorResourceAssembler = authorResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    NestedContentResource<AuthorResource> all() {
        return new NestedContentResource<AuthorResource>(
                this.authorResourceAssembler.toResources(this.authorRepository.findAll()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Resource<Author> author(@PathVariable("id") long id) {
        return this.authorResourceAssembler.toResource(findAuthorById(id));
    }

    private Author findAuthorById(long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        return author.orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }

}

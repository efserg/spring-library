package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.controller.rest.AuthorResourceAssembler.AuthorResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import space.efremov.otusspringlibrary.domain.Author;

@Component
public class AuthorResourceAssembler extends ResourceAssemblerSupport<Author, AuthorResource> {

    public AuthorResourceAssembler() {
        super(AuthorRestController.class, AuthorResource.class);
    }

    @Override
    protected AuthorResource instantiateResource(Author entity) {
        return new AuthorResource(entity);
    }

    @Override
    public AuthorResource toResource(Author entity) {
        AuthorResource resource = createResourceWithId(entity.getId(), entity);
        resource.add(linkTo(AuthorRestController.class).slash(entity.getId()).slash("books").withRel("author-books"));
        return resource;
    }

    static class AuthorResource extends Resource<Author> {
        public AuthorResource(Author content) {
            super(content);
        }
    }
}

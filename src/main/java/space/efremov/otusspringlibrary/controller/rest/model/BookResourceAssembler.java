package space.efremov.otusspringlibrary.controller.rest.model;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.controller.rest.BookRestController;
import space.efremov.otusspringlibrary.controller.rest.model.BookResourceAssembler.BookResource;
import space.efremov.otusspringlibrary.domain.Book;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BookRestController.class, BookResource.class);
    }

    @Override
    protected BookResource instantiateResource(Book entity) {
        return new BookResource(entity);
    }

    @Override
    public BookResource toResource(Book entity) {
        BookResource resource = createResourceWithId(entity.getId(), entity);
        resource.add(linkTo(BookRestController.class).slash(entity.getId()).slash("reviews").withRel("book-reviews"));
        resource.add(linkTo(BookRestController.class).slash(entity.getId()).slash("tags").withRel("book-tags"));
        return resource;
    }

    public static class BookResource extends Resource<Book> {
        public BookResource(Book content) {
            super(content);
        }
    }
}

package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.domain.Publisher;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class PublisherResourceAssembler extends ResourceAssemblerSupport<Publisher, PublisherResourceAssembler.PublisherResource> {

    public PublisherResourceAssembler() {
        super(PublisherRestController.class, PublisherResource.class);
    }

    @Override
    protected PublisherResource instantiateResource(Publisher entity) {
        return new PublisherResource(entity);
    }

    @Override
    public PublisherResource toResource(Publisher entity) {
        PublisherResource resource = createResourceWithId(entity.getId(), entity);
        resource.add(linkTo(PublisherRestController.class).slash(entity.getId()).slash("books").withRel("publisher-books"));
        return resource;
    }

    static class PublisherResource extends Resource<Publisher> {
        public PublisherResource(Publisher content) {
            super(content);
        }
    }
}

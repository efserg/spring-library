package space.efremov.otusspringlibrary.controller.rest.model;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.controller.rest.PublisherRestController;
import space.efremov.otusspringlibrary.controller.rest.model.PublisherResourceAssembler.PublisherResource;
import space.efremov.otusspringlibrary.domain.Publisher;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class PublisherResourceAssembler extends ResourceAssemblerSupport<Publisher, PublisherResource> {

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

    public static class PublisherResource extends Resource<Publisher> {
        public PublisherResource(Publisher content) {
            super(content);
        }
    }
}

package space.efremov.otusspringlibrary.controller.rest.model;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.controller.rest.TagRestController;
import space.efremov.otusspringlibrary.controller.rest.model.TagResourceAssembler.TagResource;
import space.efremov.otusspringlibrary.domain.Tag;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class TagResourceAssembler extends ResourceAssemblerSupport<Tag, TagResource> {

    public TagResourceAssembler() {
        super(TagRestController.class, TagResource.class);
    }

    @Override
    protected TagResource instantiateResource(Tag entity) {
        return new TagResource(entity);
    }

    @Override
    public TagResource toResource(Tag entity) {
        TagResource resource = createResourceWithId(entity.getId(), entity);
        resource.add(linkTo(TagRestController.class).slash(entity.getId()).slash("books").withRel("tag-books"));
        // todo: add abb-book method
        return resource;
    }

    public static class TagResource extends Resource<Tag> {
        public TagResource(Tag content) {
            super(content);
        }
    }
}

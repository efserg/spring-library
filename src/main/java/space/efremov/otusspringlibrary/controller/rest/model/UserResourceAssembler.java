package space.efremov.otusspringlibrary.controller.rest.model;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.controller.rest.UserRestController;
import space.efremov.otusspringlibrary.domain.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResourceAssembler.UserResource> {

    public UserResourceAssembler() {
        super(UserRestController.class, UserResource.class);
    }

    @Override
    protected UserResource instantiateResource(User entity) {
        return new UserResource(entity);
    }

    @Override
    public UserResource toResource(User entity) {
        UserResource resource = createResourceWithId(entity.getId(), entity);
        resource.add(linkTo(UserRestController.class).slash(entity.getId()).slash("reviews").withRel("user-reviews"));
        return resource;
    }

    public static class UserResource extends Resource<User> {
        public UserResource(User content) {
            super(content);
        }
    }
}

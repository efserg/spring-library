package space.efremov.otusspringlibrary.controller.rest.model;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.controller.rest.ReviewRestController;
import space.efremov.otusspringlibrary.controller.rest.model.ReviewResourceAssembler.ReviewResource;
import space.efremov.otusspringlibrary.domain.Review;

@Component
public class ReviewResourceAssembler extends ResourceAssemblerSupport<Review, ReviewResource> {

    public ReviewResourceAssembler() {
        super(ReviewRestController.class, ReviewResource.class);
    }

    @Override
    protected ReviewResource instantiateResource(Review entity) {
        return new ReviewResource(entity);
    }

    @Override
    public ReviewResource toResource(Review entity) {
        ReviewResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    public static class ReviewResource extends Resource<Review> {
        public ReviewResource(Review content) {
            super(content);
        }
    }
}

package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import space.efremov.otusspringlibrary.domain.Review;

@Component
public class ReviewResourceAssembler extends ResourceAssemblerSupport<Review, ReviewResourceAssembler.ReviewResource> {

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

    static class ReviewResource extends Resource<Review> {
        public ReviewResource(Review content) {
            super(content);
        }
    }
}

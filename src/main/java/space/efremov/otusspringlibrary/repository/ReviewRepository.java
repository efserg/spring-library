package space.efremov.otusspringlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import space.efremov.otusspringlibrary.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}

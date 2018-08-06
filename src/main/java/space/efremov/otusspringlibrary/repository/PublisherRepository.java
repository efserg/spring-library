package space.efremov.otusspringlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import space.efremov.otusspringlibrary.domain.Publisher;

import java.util.List;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    @Override
    List<Publisher> findAll();

}

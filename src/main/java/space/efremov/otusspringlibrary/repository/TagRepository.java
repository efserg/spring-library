package space.efremov.otusspringlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import space.efremov.otusspringlibrary.domain.Tag;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {

    @Override
    List<Tag> findAll();

}

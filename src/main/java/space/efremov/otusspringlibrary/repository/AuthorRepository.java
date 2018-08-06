package space.efremov.otusspringlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Override
    List<Author> findAll();

    @Override
    List<Author> findAllById(Iterable<Long> longs);

}

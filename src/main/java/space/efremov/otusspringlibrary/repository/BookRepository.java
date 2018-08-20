package space.efremov.otusspringlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import space.efremov.otusspringlibrary.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    List<Book> findAll();

    List<Book> findByYear(Integer year);

}

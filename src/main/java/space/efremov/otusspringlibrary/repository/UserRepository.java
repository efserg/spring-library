package space.efremov.otusspringlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import space.efremov.otusspringlibrary.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

}

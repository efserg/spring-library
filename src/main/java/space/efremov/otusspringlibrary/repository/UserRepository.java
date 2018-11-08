package space.efremov.otusspringlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import space.efremov.otusspringlibrary.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    Optional<User> findByUsername(String username);
}

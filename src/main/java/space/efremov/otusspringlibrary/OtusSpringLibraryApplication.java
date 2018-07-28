package space.efremov.otusspringlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import space.efremov.otusspringlibrary.dao.jdbc.AuthorDao;

@SpringBootApplication
public class OtusSpringLibraryApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(OtusSpringLibraryApplication.class, args);
        AuthorDao dao = context.getBean(AuthorDao.class);
        System.out.println("All count " + dao.count());
        System.out.println("Get all " + dao.getAll());
        System.out.println("Get by ID " + dao.getById(1));
    }
}

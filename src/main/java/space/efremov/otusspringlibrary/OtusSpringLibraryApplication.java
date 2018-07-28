package space.efremov.otusspringlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import space.efremov.otusspringlibrary.dao.jdbc.AuthorDao;
import space.efremov.otusspringlibrary.dao.jdbc.BookDao;

@SpringBootApplication
public class OtusSpringLibraryApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(OtusSpringLibraryApplication.class, args);
        AuthorDao authorDao = context.getBean(AuthorDao.class);
        BookDao bookDao = context.getBean(BookDao.class);

        // System.out.println("All count " + authorDao.count());
        // System.out.println("Get all " + authorDao.getAll());
        // System.out.println("Get by ID " + authorDao.getById(1));
        System.out.println("Get by ID " + bookDao.getById(1));
    }
}

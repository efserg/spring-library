package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.dao.jpa.BookJpaDao;
import space.efremov.otusspringlibrary.dao.jpa.UserJpaDao;
import space.efremov.otusspringlibrary.dao.jpa.ReviewJpaDao;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.User;
import space.efremov.otusspringlibrary.domain.Review;

import java.util.List;

@ShellComponent
@ShellCommandGroup("review")
public class ReviewConsoleController {

    private final ReviewJpaDao dao;
    private final BookJpaDao bookDao;
    private final UserJpaDao personDao;

    public ReviewConsoleController(ReviewJpaDao dao, BookJpaDao bookDao, UserJpaDao personDao) {
        this.dao = dao;
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @ShellMethod(value = "Add book review to DB.", key = "review add")
    public Review addReview(@ShellOption(help = "User ID.") Long userId,
                            @ShellOption(help = "Book ID.") Long bookId,
                            @ShellOption(help = "Your review.") String text) {
        final User user = personDao.getById(userId);
        final Book book = bookDao.getById(bookId);
        final Review review = new Review(user, text);
        book.addReview(review);
        bookDao.update(book);
        return review;
    }

    @ShellMethod(value = "Get all authors from DB.", key = "review list")
    public List<Review> list(@ShellOption(help = "Book ID.") Long bookId) {
        final Book book = bookDao.getById(bookId);
        return book.getReviews();
    }
}

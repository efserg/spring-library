package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.Review;
import space.efremov.otusspringlibrary.domain.User;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.BookRepository;
import space.efremov.otusspringlibrary.repository.ReviewRepository;
import space.efremov.otusspringlibrary.repository.UserRepository;

import java.util.List;

@ShellComponent
@ShellCommandGroup("review")
@Transactional(readOnly = true)
public class ReviewConsoleController {

    private final BookRepository bookRepository;
    private final UserRepository personDao;

    public ReviewConsoleController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.personDao = userRepository;
    }

    @Transactional
    @ShellMethod(value = "Add book review to DB.", key = {"review add", "review-add"})
    public Review addReview(@ShellOption(help = "User ID.", value = {"user-id", "uid", "userId"}) Long userId,
                            @ShellOption(help = "Book ID.", value = {"book-id", "bid", "bookId"}) Long bookId,
                            @ShellOption(help = "Your review.", value = {"text", "review-text", "reviewText"}) String text) {
        final User user = personDao.findById(userId).orElseThrow(EntityNotFoundException::new);
        final Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        final Review review = new Review(user, text);
        book.addReview(review);
        bookRepository.save(book);
        return review;
    }

    @ShellMethod(value = "Get all authors from DB.", key = {"review list", "review-list"})
    public List<Review> list(@ShellOption(help = "Book ID.", value = {"book-id", "bid", "bookId"}) Long bookId) {
        final Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        return book.getReviews();
    }
}

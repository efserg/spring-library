package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import space.efremov.otusspringlibrary.dao.jpa.BookJpaDao;
import space.efremov.otusspringlibrary.dao.jpa.PublisherJpaDao;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.List;

import static space.efremov.otusspringlibrary.controller.impl.console.AppConstant.ENTITY_NOT_FOUND_ERROR;

@ShellComponent
@ShellCommandGroup("book")
public class BookConsoleController {

    private final BookJpaDao dao;
    private final PublisherJpaDao publisherDao;

    public BookConsoleController(BookJpaDao dao, PublisherJpaDao publisherDao) {
        this.dao = dao;
        this.publisherDao = publisherDao;
    }

    @ShellMethod(value = "Add book to DB.", key = "book add")
    public Book add(@ShellOption(help = "Book title. Use quotes if you need, e.g. \"Large Scale Machine Learning with Python\"") String title,
                    @ShellOption(help = "Book ISBN.") String isbn,
                    @ShellOption(help = "Book year.") Integer year,
                    @ShellOption(help = "Publisher ID. Use \"publisher list\" command to find ID") Long publisherId) {
        return dao.insert(new Book(title, isbn, year, publisherDao.getById(publisherId)));
    }

    @ShellMethod(value = "Remove book from DB.", key = "book remove")
    public String remove(@ShellOption(help = "Book ID. You can use \"book list\" command to found ID") Long id) {
        try {
            final Book book = dao.getById(id);
            dao.delete(book);
            return "Success";
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get book from DB.", key = "book get")
    public String get(@ShellOption(help = "book ID.") Long id) {
        try {
            return dao.getById(id).toString();
        } catch (EntityNotFoundException e) {
            return ENTITY_NOT_FOUND_ERROR;
        }
    }

    @ShellMethod(value = "Get all books from DB.", key = "book list")
    public List<Book> list() {
        return dao.getAll();
    }
}

package space.efremov.otusspringlibrary.controller.impl.console;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;
import space.efremov.otusspringlibrary.repository.BookRepository;
import space.efremov.otusspringlibrary.repository.PublisherRepository;
import space.efremov.otusspringlibrary.repository.TagRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("book")
@Transactional(readOnly = true)
public class BookConsoleController {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;

    public BookConsoleController(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository, TagRepository tagRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    @ShellMethod(value = "Add book to DB.", key = {"book add", "book-add"})
    public Book add(@ShellOption(help = "Book title. Use quotes if you need, e.g. \"Large Scale Machine Learning with Python\".", value = {"title", "book-title", "bookTitle"}) String title,
                    @ShellOption(help = "Book ISBN.", value = {"isbn", "book-isbn", "bookIsbn"}) String isbn,
                    @ShellOption(help = "Book year.", value = {"year", "book-year", "bookYear"}) Integer year,
                    @ShellOption(help = "Publisher ID. Use \"publisher list\" command to find publisher ID", value = {"publisher-id", "pid", "publisherId"}) Long publisherId,
                    @ShellOption(help = "Author IDs list, separated by \",\". Use \"author list\" command to find author ID", defaultValue = "", value = {"authors", "author-list", "author-ids, authorIds"}) String authorIds,
                    @ShellOption(help = "Tag IDs list, separated by \",\". Use \"tag list\" command to find tag ID", defaultValue = "", value = {"tags", "tag-list", "tag-ids, tagIds"}) String tagIds
    ) {
        final Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(EntityNotFoundException::new);
        final List<Author> authors = Arrays.stream(authorIds.split(",")).map(Long::new).map(authorRepository::findById).map(a -> a.orElseThrow(EntityNotFoundException::new)).collect(Collectors.toList());
        final List<Tag> tags = Arrays.stream(tagIds.split(",")).map(Long::new).map(tagRepository::findById).map(a -> a.orElseThrow(EntityNotFoundException::new)).collect(Collectors.toList());
        return bookRepository.save(new Book(title, isbn, year, publisher, tags, authors));
    }

    @Transactional
    @ShellMethod(value = "Remove book from DB.", key = {"book remove", "book-remove"})
    public void remove(@ShellOption(help = "Book ID. You can use \"book list\" command to found ID", value = {"book-id", "bid", "bookId", "id"}) Long id) {
        bookRepository.delete(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @ShellMethod(value = "Get book from DB.", key = {"book get", "book-get"})
    public Book get(@ShellOption(help = "book ID.", value = {"book-id", "bid", "bookId", "id"}) Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ShellMethod(value = "Get all books from DB.", key = {"book list", "book-list"})
    public List<Book> list() {
        return bookRepository.findAll();
    }
}

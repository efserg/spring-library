package space.efremov.otusspringlibrary.service;

import org.springframework.stereotype.Service;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;
import space.efremov.otusspringlibrary.repository.BookRepository;
import space.efremov.otusspringlibrary.repository.PublisherRepository;
import space.efremov.otusspringlibrary.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public LibraryService(AuthorRepository authorRepository, TagRepository tagRepository, PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public Tag findTagById(long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElseThrow(() -> new EntityNotFoundException(Tag.class, id));
    }

    public Author findAuthorById(long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }

    public Publisher findPublisherById(long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        return publisher.orElseThrow(() -> new EntityNotFoundException(Publisher.class, id));
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public void removeAuthor(long id) {
        authorRepository.delete(findAuthorById(id));
    }

    public Author createAuthor(String name) {
        final Author author = new Author(name);
        return authorRepository.save(author);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public void removeTag(long id) {
        tagRepository.delete(findTagById(id));
    }

    public Tag createTag(String tag) {
        final Tag entity = new Tag(tag);
        return tagRepository.save(entity);
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public void removePublisher(long id) {
        publisherRepository.delete(findPublisherById(id));
    }

    public Publisher createPublisher(String name) {
        final Publisher entity = new Publisher(name);
        return publisherRepository.save(entity);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new EntityNotFoundException(Book.class, id));
    }

    public void removeBook(long id) {
        bookRepository.delete(findBookById(id));
    }

    public Book createBook(String title, String isbn, Integer year, Long publisherId, List<Long> authorIds, List<Long> tagIds) {
        final List<Author> authors = getAuthors(authorIds);
        final List<Tag> tags = getTags(tagIds);
        final Publisher publisher = findPublisherById(publisherId);
        final Book book = new Book(title, isbn, year, publisher, tags, authors);
        return bookRepository.save(book);
    }

    private List<Tag> getTags(List<Long> tagIds) {
        final List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size())
            throw new EntityNotFoundException(Tag.class);
        return tags;
    }

    private List<Author> getAuthors(List<Long> authorIds) {
        final List<Author> authors = authorRepository.findAllById(authorIds);
        if (authors.size() != authorIds.size())
            throw new EntityNotFoundException(Author.class);
        return authors;
    }

    public Book updateBook(long id, String title, String isbn, Integer year, Long publisherId, List<Long> authorIds, List<Long> tagIds) {
        final Book book = findBookById(id);
        final List<Author> authors = getAuthors(authorIds);
        final List<Tag> tags = getTags(tagIds);
        final Publisher publisher = findPublisherById(publisherId);

        book.setTitle(title);
        book.setPublisher(publisher);
        book.setIsbn(isbn);
        book.setYear(year);
        book.setAuthors(authors);
        book.setTags(tags);

        return bookRepository.save(book);

    }
}

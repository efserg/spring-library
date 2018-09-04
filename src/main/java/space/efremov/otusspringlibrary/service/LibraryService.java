package space.efremov.otusspringlibrary.service;

import org.springframework.stereotype.Service;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;
import space.efremov.otusspringlibrary.repository.AuthorRepository;
import space.efremov.otusspringlibrary.repository.PublisherRepository;
import space.efremov.otusspringlibrary.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final PublisherRepository publisherRepository;

    public LibraryService(AuthorRepository authorRepository, TagRepository tagRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.publisherRepository = publisherRepository;
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
        authorRepository.save(author);
        return author;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public void removeTag(long id) {
        tagRepository.delete(findTagById(id));
    }

    public Tag createTag(String tag) {
        final Tag entity = new Tag(tag);
        tagRepository.save(entity);
        return entity;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public void removePublisher(long id) {
        publisherRepository.delete(findPublisherById(id));
    }

    public Publisher createPublisher(String name) {
        final Publisher entity = new Publisher(name);
        publisherRepository.save(entity);
        return entity;
    }
}

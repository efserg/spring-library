package space.efremov.otusspringlibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BookJpaDao implements Dao<Book> {

    private final EntityManager em;

    public BookJpaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Long count() {
        final TypedQuery<Long> query = em.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Book insert(Book entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(Book entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public Book getById(Long id) {
        final Book book = em.find(Book.class, id);
        if (book == null) {
            throw new EntityNotFoundException();
        }
        return book;
    }

    @Override
    @Transactional
    public List<Book> getAll() {
        final TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }
}

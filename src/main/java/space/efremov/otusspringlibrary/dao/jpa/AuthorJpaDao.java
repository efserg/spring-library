package space.efremov.otusspringlibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AuthorJpaDao implements Dao<Author> {

    private final EntityManager em;

    public AuthorJpaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Long count() {
        final TypedQuery<Long> query = em.createQuery("select count(a) from Author a", Long.class);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Author insert(Author entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(Author entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public Author getById(Long id) {
        final Author author = em.find(Author.class, id);
        if (author == null) {
            throw new EntityNotFoundException();
        }
        return author;
    }

    @Override
    @Transactional
    public List<Author> getAll() {
        final TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}

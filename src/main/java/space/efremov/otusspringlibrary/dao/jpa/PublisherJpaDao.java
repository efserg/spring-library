package space.efremov.otusspringlibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PublisherJpaDao implements Dao<Publisher> {

    private final EntityManager em;

    public PublisherJpaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Long count() {
        final TypedQuery<Long> query = em.createQuery("select count(p) from Publisher p", Long.class);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Publisher insert(Publisher entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(Publisher entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public Publisher getById(Long id) {
        final Publisher publisher = em.find(Publisher.class, id);
        if (publisher == null) {
            throw new EntityNotFoundException();
        }
        return publisher;
    }

    @Override
    @Transactional
    public List<Publisher> getAll() {
        final TypedQuery<Publisher> query = em.createQuery("select p from Publisher p", Publisher.class);
        return query.getResultList();
    }
}

package space.efremov.otusspringlibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TagJpaDao implements Dao<Tag> {

    private final EntityManager em;

    public TagJpaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Long count() {
        final TypedQuery<Long> query = em.createQuery("select count(t) from Tag t", Long.class);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Tag insert(Tag entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(Tag entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public Tag getById(Long id) {
        final Tag author = em.find(Tag.class, id);
        if (author == null) {
            throw new EntityNotFoundException();
        }
        return author;
    }

    @Override
    @Transactional
    public List<Tag> getAll() {
        final TypedQuery<Tag> query = em.createQuery("select t from Tag t", Tag.class);
        return query.getResultList();
    }
}

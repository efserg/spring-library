package space.efremov.otusspringlibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Review;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ReviewJpaDao implements Dao<Review> {

    private final EntityManager em;

    public ReviewJpaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Long count() {
        final TypedQuery<Long> query = em.createQuery("select count(r) from Review r", Long.class);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Review insert(Review entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(Review entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public Review getById(Long id) {
        final Review review = em.find(Review.class, id);
        if (review == null) {
            throw new EntityNotFoundException();
        }
        return review;
    }

    @Override
    @Transactional
    public List<Review> getAll() {
        final TypedQuery<Review> query = em.createQuery("select r from Review r", Review.class);
        return query.getResultList();
    }

}

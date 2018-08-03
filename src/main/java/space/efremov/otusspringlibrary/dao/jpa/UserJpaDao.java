package space.efremov.otusspringlibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.User;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserJpaDao implements Dao<User> {

    private final EntityManager em;

    public UserJpaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Long count() {
        final TypedQuery<Long> query = em.createQuery("select count(a) from Person a", Long.class);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public User insert(User entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(User entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public User getById(Long id) {
        final User user = em.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    @Override
    @Transactional
    public List<User> getAll() {
        final TypedQuery<User> query = em.createQuery("select a from Person a", User.class);
        return query.getResultList();
    }
}

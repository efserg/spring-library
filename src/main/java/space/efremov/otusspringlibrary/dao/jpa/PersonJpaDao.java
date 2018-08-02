package space.efremov.otusspringlibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.dao.Dao;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Person;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PersonJpaDao implements Dao<Person> {

    private final EntityManager em;

    public PersonJpaDao(EntityManager em) {
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
    public Person insert(Person entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(Person entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public Person getById(Long id) {
        final Person person = em.find(Person.class, id);
        if (person == null) {
            throw new EntityNotFoundException();
        }
        return person;
    }

    @Override
    @Transactional
    public List<Person> getAll() {
        final TypedQuery<Person> query = em.createQuery("select a from Person a", Person.class);
        return query.getResultList();
    }
}

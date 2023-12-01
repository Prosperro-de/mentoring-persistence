package org.example.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.example.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.example.utils.ReflectionUtil;
import org.hibernate.Transaction;


public abstract class BaseDAO<E> {



    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");

    protected EntityManager entityManager = emf.createEntityManager();

    private static final ReflectionUtil reflectionUtil = new ReflectionUtil();


@Transactional
    public E getEntityByID(Long id, Class<E> entityClass) {
        return entityManager.find(entityClass, id);
    }

    protected void saveEntity(E entity) {
        System.out.printf("saving entity: %s%n", entity);

        entityManager.persist(entity);
        System.out.printf("sucsesfully saved entity :%s%n", entity);
    }

    @Transactional
    public void deleteEntity(E entity) {
        entityManager.merge(entity);
        entityManager.remove(entity);
    }

    //TODO figure out way how to do good "find" which will return E not Class<?>
    public void updateEntity(E entity, Class<E> entityClass) {
        Long id = reflectionUtil.getIdField(entity);
        E entityFromDB = getEntityByID(id, entityClass);
        E entityNew = reflectionUtil.copyValues(entityFromDB, entity);

        saveEntity(entityNew);

    }

}

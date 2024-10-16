package dao.impl;

import java.io.Serializable;
import java.util.List;

import dao.interf.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.HibernateUtil;

public class GenericDAOHibernateJPA<T> implements GenericDAO<T> {

  protected Class<T> persistentClass;

  public GenericDAOHibernateJPA(Class<T> persistentClass) {
    this.persistentClass = persistentClass;
  }

  @Override
  public boolean exist(Long id) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      T entity = em.find(persistentClass, id);
      return entity != null;
    }
  }

  @Override
  public T save(T entity) {
    EntityManager em = HibernateUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      em.persist(entity);
      tx.commit();
    } catch (RuntimeException e) {
      if (tx.isActive())
        tx.rollback();
      throw e; // Manejar la excepci�n
    } finally {
      em.close();
    }
    return entity;
  }

  @Override
  public T update(T entity) {
    EntityManager em = HibernateUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    T mergedEntity = null;
    try {
      tx.begin();
      mergedEntity = em.merge(entity);
      tx.commit();
    } catch (RuntimeException e) {
      if (tx.isActive())
        tx.rollback();
      throw e;
    } finally {
      em.close();
    }
    return mergedEntity;
  }

  @Override
  public void delete(T entity) {
    EntityManager em = HibernateUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      em.remove(em.merge(entity)); // `merge` para asegurar que est� gestionado
      tx.commit();
    } catch (RuntimeException e) {
      if (tx.isActive())
        tx.rollback();
      throw e;
    } finally {
      em.close();
    }
  }

  @Override
  public T delete(Long id) {
    EntityManager em = HibernateUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    T entity = null;
    try {
      entity = em.find(persistentClass, id);
      if (entity != null) {
        tx.begin();
        em.remove(entity);
        tx.commit();
      }
    } catch (RuntimeException e) {
      if (tx.isActive())
        tx.rollback();
      throw e;
    } finally {
      em.close();
    }
    return entity;
  }

  @Override
  public T get(Serializable id) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      return em.find(persistentClass, id);
    }
  }

  @Override
  public List<T> getAll(String column) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      return em.createQuery("SELECT e FROM " + persistentClass.getSimpleName()
          + " e ORDER BY e." + column, persistentClass).getResultList();
    }
  }

  @Override
  public List<T> getAll() {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      return em.createQuery(
          "SELECT e FROM " + persistentClass.getSimpleName() + " e",
          persistentClass).getResultList();
    }
  }
}

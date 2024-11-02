package com.ttps.quecomemos.dao.hibernateJPA.impl;

import java.util.List;

import com.ttps.quecomemos.dao.hibernateJPA.interf.FoodDAO;
import com.ttps.quecomemos.model.Food;
import com.ttps.quecomemos.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class FoodDAOHibernateJPA extends GenericDAOHibernateJPA<Food>
    implements FoodDAO {
  public FoodDAOHibernateJPA() {
    super(Food.class);
  }

  @Override
  public Food getByName(String name) {
    EntityManager em = HibernateUtil.getEntityManager();
    try {
      TypedQuery<Food> query = em
          .createQuery("SELECT f FROM Food f WHERE f.name = :name", Food.class);
      query.setParameter("name", name);
      List<Food> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
    } catch (Exception e) {
      return null;
    } finally {
      em.close();
    }
  }

  @Override
  public List<Food> getByType(String type) {
    EntityManager em = HibernateUtil.getEntityManager();
    try {
      TypedQuery<Food> query = em
          .createQuery("SELECT f FROM Food f WHERE f.type = :type", Food.class);
      query.setParameter("type", type);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    } finally {
      em.close();
    }
  }

  @Override
  public List<Food> getVegetarians(Boolean isVegetarian) {
    EntityManager em = HibernateUtil.getEntityManager();
    try {
      TypedQuery<Food> query = em.createQuery(
          "SELECT f FROM Food f WHERE f.isVegetarian = :isVegetarian",
          Food.class);
      query.setParameter("isVegetarian", isVegetarian);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    } finally {
      em.close();
    }
  }

  @Override
  public List<Food> getNonVegetarians(Boolean isVegetarian) {
    EntityManager em = HibernateUtil.getEntityManager();
    try {
      TypedQuery<Food> query = em.createQuery(
          "SELECT f FROM Food f WHERE f.isVegetarian != :isVegetarian",
          Food.class);
      query.setParameter("isVegetarian", isVegetarian);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    } finally {
      em.close();
    }
  }

}

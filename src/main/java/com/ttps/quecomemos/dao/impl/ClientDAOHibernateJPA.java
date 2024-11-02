package com.ttps.quecomemos.dao.impl;

import java.util.List;

import com.ttps.quecomemos.dao.interf.ClientDAO;
import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClientDAOHibernateJPA extends GenericDAOHibernateJPA<Client>
    implements ClientDAO {

  public ClientDAOHibernateJPA() {
    super(Client.class);
  }

  @Override
  public User getByEmail(String email) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Client> query = em.createQuery(
          "SELECT c FROM Client c WHERE c.email = :email", Client.class);
      query.setParameter("email", email);
      List<Client> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public User getByDNI(String dni) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Client> query = em.createQuery(
          "SELECT u FROM Client u WHERE u.dni = :dni", Client.class);
      query.setParameter("dni", dni);
      List<Client> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public User getByEmailAndPassword(String email, String password) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Client> query = em.createQuery(
          "SELECT u FROM Client u WHERE u.email = :email AND u.password = :password",
          Client.class);
      query.setParameter("email", email);
      query.setParameter("password", password);
      List<Client> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
    } catch (Exception e) {
      return null;
    }
  }

}

package dao.impl;

import java.util.List;

import dao.interf.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.User;
import util.HibernateUtil;

public class UserDAOHibernateJPA extends GenericDAOHibernateJPA<User>
    implements UserDAO {

  public UserDAOHibernateJPA() {
    super(User.class);
  }

  @Override
  public User getByEmail(String email) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<User> query = em.createQuery(
          "SELECT u FROM User u WHERE u.email = :email", User.class);
      query.setParameter("email", email);
      List<User> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<User> getByRole(String role) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<User> query = em
          .createQuery("SELECT u FROM User u WHERE u.role = :role", User.class);
      query.setParameter("role", role);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public User getByDNI(String dni) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<User> query = em
          .createQuery("SELECT u FROM User u WHERE u.dni = :dni", User.class);
      query.setParameter("dni", dni);
      List<User> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
    } catch (Exception e) {
      return null;
    }
  }
}

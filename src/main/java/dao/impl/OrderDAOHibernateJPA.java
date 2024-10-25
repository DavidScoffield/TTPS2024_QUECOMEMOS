package dao.impl;

import java.util.Date;
import java.util.List;

import dao.interf.OrderDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Order;
import util.HibernateUtil;

public class OrderDAOHibernateJPA extends GenericDAOHibernateJPA<Order>
    implements OrderDAO {

  public OrderDAOHibernateJPA() {
    super(Order.class);
  }

  @Override
  public List<Order> getByEmissionDate(Date date) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.emissionDate = :date", Order.class);
      query.setParameter("date", date);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

  public List<Order> getByClientId(Long clientId) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.client.id = :clientId", Order.class);
      query.setParameter("clientId", clientId);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

}

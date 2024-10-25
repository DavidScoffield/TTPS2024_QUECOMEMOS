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

  @Override
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

  @Override
  public List<Order> getByDeliveryDate(Date date) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.deliveryDate = :date", Order.class);
      query.setParameter("date", date);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<Order> getHigherTotal(Float total) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.total > :total", Order.class);
      query.setParameter("total", total);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<Order> getLowerTotal(Float total) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.total < :total", Order.class);
      query.setParameter("total", total);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<Order> getByPaymentMethod(String paymentMethod) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.paymentMethod = :paymentMethod",
          Order.class);
      query.setParameter("paymentMethod", paymentMethod);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<Order> getBetweenEmissionDates(Date startDate, Date endDate) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.emissionDate BETWEEN :startDate AND :endDate",
          Order.class);
      query.setParameter("startDate", startDate);
      query.setParameter("endDate", endDate);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }

  }

  @Override
  public List<Order> getBetweenDeliveryDates(Date startDate, Date endDate) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Order> query = em.createQuery(
          "SELECT o FROM Order o WHERE o.deliveryDate BETWEEN :startDate AND :endDate",
          Order.class);
      query.setParameter("startDate", startDate);
      query.setParameter("endDate", endDate);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

}

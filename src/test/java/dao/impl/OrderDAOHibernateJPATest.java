package dao.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Order;
import util.HibernateUtil;

class OrderDAOHibernateJPATest {

  private EntityManager em;
  private OrderDAOHibernateJPA orderDAO;
  private Order order1;
  private Order order2;
  private Order order3;

  @BeforeEach
  void setUp() {
    em = HibernateUtil.getEntityManager();
    orderDAO = new OrderDAOHibernateJPA();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    // Creación de órdenes de prueba con fechas específicas
    order1 = new Order();
    order1.setEmissionDate(convertToDate(LocalDate.of(2024, 10, 25))); // Fecha de emisión específica
    order1.setDeliveryDate(convertToDate(LocalDate.of(2024, 10, 21))); // Fecha de entrega específica
    order1.setTotal(100.0f);
    order1.setPaymentMethod("Credit Card");

    order2 = new Order();
    order2.setEmissionDate(convertToDate(LocalDate.of(2024, 10, 20))); // Fecha de emisión específica
    order2.setDeliveryDate(convertToDate(LocalDate.of(2024, 10, 22))); // Fecha de entrega específica
    order2.setTotal(150.0f);
    order2.setPaymentMethod("PayPal");

    order3 = new Order();
    order3.setEmissionDate(convertToDate(LocalDate.of(2024, 10, 19))); // Fecha de emisión específica
    order3.setDeliveryDate(convertToDate(LocalDate.of(2024, 10, 21))); // Fecha de entrega específica
    order3.setTotal(50.0f);
    order3.setPaymentMethod("Cash");

    em.persist(order1);
    em.persist(order2);
    em.persist(order3);

    tx.commit();
  }

  @AfterEach
  void tearDown() {
    // Limpiar los datos después de cada prueba
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createQuery("DELETE FROM Order").executeUpdate(); // Elimina todas las órdenes
    tx.commit();
    em.close();
  }

  @Test
  void testGetByEmissionDate_Found() {
    List<Order> orders = orderDAO.getByEmissionDate(order1.getEmissionDate());
    assertNotNull(orders);
    assertEquals(1, orders.size());
    assertEquals(order1.getTotal(), orders.get(0).getTotal());
  }

}

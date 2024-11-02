package com.ttps.quecomemos.dao.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ttps.quecomemos.model.Order;
import com.ttps.quecomemos.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
    order1.setEmissionDate(convertToDate(LocalDate.of(2024, 10, 25)));
    order1.setDeliveryDate(convertToDate(LocalDate.of(2024, 10, 21)));
    order1.setTotal(100.0f);
    order1.setPaymentMethod("Credit Card");

    order2 = new Order();
    order2.setEmissionDate(convertToDate(LocalDate.of(2024, 10, 20)));
    order2.setDeliveryDate(convertToDate(LocalDate.of(2024, 10, 22)));
    order2.setTotal(150.0f);
    order2.setPaymentMethod("PayPal");

    order3 = new Order();
    order3.setEmissionDate(convertToDate(LocalDate.of(2024, 10, 19)));
    order3.setDeliveryDate(convertToDate(LocalDate.of(2024, 10, 21)));
    order3.setTotal(50.0f);
    order3.setPaymentMethod("Cash");

    em.persist(order1);
    em.persist(order2);
    em.persist(order3);

    tx.commit();
  }

  @AfterEach
  void tearDown() {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createQuery("DELETE FROM Order").executeUpdate();
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

  @Test
  void testGetByEmissionDate_NotFound() {
    LocalDate nonExistentDate = LocalDate.of(3000, 1, 1);
    List<Order> orders = orderDAO
        .getByEmissionDate(convertToDate(nonExistentDate));
    assertNotNull(orders);
    assertEquals(0, orders.size());
  }

  // @Test
  // void testGetByClientId() {
  // // Asumiendo que tienes un m?todo para obtener un ID de cliente; debes persistir un
  // // cliente antes.
  // // Long clientId = 1L; // ID ficticio para la prueba
  // // List<Order> orders = orderDAO.getByClientId(clientId);
  // // assertNotNull(orders);
  // // assertEquals(expectedSize, orders.size());
  // }

  @Test
  void testGetHigherTotal() {
    List<Order> orders = orderDAO.getHigherTotal(75.0f);
    assertEquals(2, orders.size()); // order2 y order1
  }

  @Test
  void testGetLowerTotal() {
    List<Order> orders = orderDAO.getLowerTotal(75.0f);
    assertEquals(1, orders.size()); // solo order3
  }

  @Test
  void testGetByPaymentMethod() {
    List<Order> orders = orderDAO.getByPaymentMethod("PayPal");
    assertNotNull(orders);
    assertEquals(1, orders.size());
    assertEquals("PayPal", orders.get(0).getPaymentMethod());
  }

  @Test
  void testGetBetweenEmissionDates() {
    LocalDate startDate = LocalDate.of(2024, 10, 18); // Fecha de inicio
    LocalDate endDate = LocalDate.of(2024, 10, 21); // Fecha de fin

    List<Order> orders = orderDAO.getBetweenEmissionDates(
        convertToDate(startDate), convertToDate(endDate));
    assertEquals(2, orders.size()); // order1 y order2 deberían estar en el rango
  }

  @Test
  void testGetBetweenDeliveryDates() {
    LocalDate startDate = LocalDate.of(2024, 10, 20); // Fecha de inicio
    LocalDate endDate = LocalDate.of(2024, 10, 21); // Fecha de fin

    List<Order> orders = orderDAO.getBetweenDeliveryDates(
        convertToDate(startDate), convertToDate(endDate));
    assertEquals(2, orders.size()); // order1 y order3 deberían estar en el rango
  }

  // Método auxiliar para convertir LocalDate a Date
  private Date convertToDate(LocalDate localDate) {
    return Date
        .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

}

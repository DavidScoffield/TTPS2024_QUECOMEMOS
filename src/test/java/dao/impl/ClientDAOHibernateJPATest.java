package dao.impl;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Client;
import model.ShoppingCart;
import util.HibernateUtil;

public class ClientDAOHibernateJPATest {
  private EntityManager em;
  private ClientDAOHibernateJPA clientDAO;

  @BeforeEach
  void setUp() {
    em = HibernateUtil.getEntityManager();
    clientDAO = new ClientDAOHibernateJPA();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    ShoppingCart cart1 = new ShoppingCart();

    Client client1 = new Client("123456", "client1", "client1@gmail.com",
        "password1", "client", "photo1", cart1);

    cart1.setClient(client1);

    em.persist(client1);
    em.persist(cart1);

    tx.commit();
  }

  @AfterEach
  void tearDown() {
    // Limpiar los datos después de cada prueba
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createQuery("DELETE FROM ShoppingCart").executeUpdate();
    em.createQuery("DELETE FROM Client").executeUpdate();
    em.createQuery("DELETE FROM User").executeUpdate();

    tx.commit();
    em.close();
  }

  @Test
  void testGetByEmail() {
    Client client = (Client) clientDAO.getByEmail("client1@gmail.com");
    assertNotNull(client);
    assertEquals("client1@gmail.com", client.getEmail());
  }

  @Test
  void testGetByDNI() {
    Client client = (Client) clientDAO.getByDNI("123456");
    assertNotNull(client);
    assertEquals("123456", client.getDni());
  }

  @Test
  void testGetByEmailAndPassword() {
    Client client = (Client) clientDAO
        .getByEmailAndPassword("client1@gmail.com", "password1");
    assertNotNull(client);
    assertEquals("client1@gmail.com", client.getEmail());

    client = (Client) clientDAO.getByEmailAndPassword("user2@gmail.com",
        "nonExist");
    assertNull(client);
  }

}

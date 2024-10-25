package dao.impl;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.User;
import util.HibernateUtil;

class UserDAOHibernateJPATest {

  private EntityManager em;
  private UserDAOHibernateJPA userDAO;

  @BeforeEach
  void setUp() {
    em = HibernateUtil.getEntityManager();
    userDAO = new UserDAOHibernateJPA();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    User user1 = new User("123456", "administrador", "user1@gmail.com",
        "password1", "ADMIN");
    User user2 = new User("654321", "usuario", "user2@gmail.com", "password2",
        "USER");

    em.persist(user1);
    em.persist(user2);

    tx.commit();
  }

  @AfterEach
  void tearDown() {
    // Limpiar los datos después de cada prueba
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createQuery("DELETE FROM User").executeUpdate();
    tx.commit();
    em.close();
  }

  @Test
  void testGetByEmail() {
    User user = userDAO.getByEmail("user1@gmail.com");
    assertNotNull(user);
    assertEquals("user1@gmail.com", user.getEmail());
  }

  @Test
  void testGetByRole() {
    List<User> admins = userDAO.getByRole("ADMIN");
    assertEquals(1, admins.size());
    assertEquals("user1@gmail.com", admins.get(0).getEmail());
  }

  @Test
  void testGetByDNI() {
    User user = userDAO.getByDNI("123456");
    assertNotNull(user);
    assertEquals("123456", user.getDni());
  }

  @Test
  void testGetByEmailAndPassword() {
    User user = userDAO.getByEmailAndPassword("user1@gmail.com", "password1");
    assertNotNull(user);
    assertEquals("user1@gmail.com", user.getEmail());

    user = userDAO.getByEmailAndPassword("user2@gmail.com", "nonExist");
    assertNull(user);
  }

}

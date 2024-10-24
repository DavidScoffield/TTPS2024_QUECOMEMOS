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
import model.Food;
import util.HibernateUtil;

class FoodDAOHibernateJPATest {

  private EntityManager em;
  private FoodDAOHibernateJPA foodDAO;

  @BeforeEach
  void setUp() {
    em = HibernateUtil.getEntityManager();
    foodDAO = new FoodDAOHibernateJPA();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Food food1 = new Food("Pizza", false, "Fast Food");
    Food food2 = new Food("Salad", false, "Vegetable");
    Food food3 = new Food("Vegan potato omelette", true, "Vegetable");

    em.persist(food1);
    em.persist(food2);
    em.persist(food3);

    tx.commit();
  }

  @AfterEach
  void tearDown() {
    // Limpiar los datos después de cada prueba
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createQuery("DELETE FROM Food").executeUpdate(); // Elimina todos los alimentos
    tx.commit();
    em.close();
  }

  @Test
  void testGetByName_Found() {
    Food food = foodDAO.getByName("Pizza");
    assertNotNull(food);
    assertEquals("Pizza", food.getName());
  }

  @Test
  void testGetByName_NotFound() {
    Food food = foodDAO.getByName("NonExistentFood");
    assertNull(food); // Debe ser nulo
  }

  @Test
  void testGetByType() {
    List<Food> vegetables = foodDAO.getByType("Vegetable");
    assertEquals(2, vegetables.size());
    assertEquals("Salad", vegetables.get(0).getName());
    assertEquals("Vegan potato omelette", vegetables.get(1).getName());
  }

  @Test
  void testGetVegetarians() {
    List<Food> vegetarians = foodDAO.getVegetarians(true);
    assertEquals(1, vegetarians.size());
    assertEquals("Vegan potato omelette", vegetarians.get(0).getName());
  }

  @Test
  void testGetNonVegetarians() {
    List<Food> nonVegetarians = foodDAO.getNonVegetarians(true);
    assertEquals(2, nonVegetarians.size());
    assertEquals("Pizza", nonVegetarians.get(0).getName());
    assertEquals("Salad", nonVegetarians.get(1).getName());
  }
}

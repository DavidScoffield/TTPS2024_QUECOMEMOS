package dao.impl;

import java.util.List;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Food;
import model.Menu;
import util.HibernateUtil;

public class MenuDAOHibernateJPATest {

  private EntityManager em;
  private MenuDAOHibernateJPA menuDAO;

  @BeforeEach
  void setUp() {
    em = HibernateUtil.getEntityManager();
    menuDAO = new MenuDAOHibernateJPA();
  }

  @AfterEach
  void tearDown() {
    // Limpiar los datos después de cada prueba
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createQuery("DELETE FROM Menu").executeUpdate(); // Elimina todos los alimentos
    tx.commit();
    em.close();
  }

  @Test
  void testGetByName() {
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Menu menu = new Menu();
    menu.setName("Test Menu");
    em.persist(menu);

    tx.commit();

    Menu result = menuDAO.getByName("Test Menu");
    assertEquals("Test Menu", result.getName());

    result = menuDAO.getByName("Nonexistent Menu");
    assertNull(result);
  }

  @Test
  void testGetVegetarians() {
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    // Create vegetarian food
    Food vegetarianFood = new Food("Vegetarian Food", true, "Vegetable");
    em.persist(vegetarianFood);

    Food veganFood = new Food("Vegan Food", true, "Vegetable");
    em.persist(veganFood);

    // Create vegetarian menu
    Menu vegetarianMenu = new Menu();
    vegetarianMenu.setName("Vegetarian Menu");
    vegetarianMenu.setFoods(List.of(vegetarianFood, veganFood));
    em.persist(vegetarianMenu);

    transaction.commit();

    List<Menu> vegetarians = menuDAO.getVegetarians(true);
    assertEquals(1, vegetarians.size());
    assertEquals("Vegetarian Menu", vegetarians.get(0).getName());

    assertTrue(vegetarians.get(0).getFoods().stream()
        .anyMatch(food -> "Vegetarian Food".equals(food.getName())));
    assertTrue(vegetarians.get(0).getFoods().stream()
        .anyMatch(food -> "Vegan Food".equals(food.getName())));
  }

  @Test
  void testGetNonVegetarians() {
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    // Create non-vegetarian food
    Food nonVegetarianFood = new Food("Non-Vegetarian Food", false, "Meat");
    em.persist(nonVegetarianFood);

    Food veganFood = new Food("Vegan Food", true, "Vegetable");
    em.persist(veganFood);

    // Create non-vegetarian menu
    Menu nonVegetarianMenu = new Menu();
    nonVegetarianMenu.setName("Non-Vegetarian Menu");
    nonVegetarianMenu.setFoods(List.of(nonVegetarianFood, veganFood));
    em.persist(nonVegetarianMenu);

    transaction.commit();

    List<Menu> nonVegetarians = menuDAO.getVegetarians(false);
    assertEquals(1, nonVegetarians.size());
    assertEquals("Non-Vegetarian Menu", nonVegetarians.get(0).getName());
    assertTrue(nonVegetarians.get(0).getFoods().stream()
        .anyMatch(food -> "Non-Vegetarian Food".equals(food.getName())));
    assertTrue(nonVegetarians.get(0).getFoods().stream()
        .anyMatch(food -> "Vegan Food".equals(food.getName())));
  }
}

package com.ttps.quecomemos.dao.impl;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.ttps.quecomemos.dao.impl.hibernateJPA.FoodDAOHibernateJPA;
import com.ttps.quecomemos.model.Food;

import jakarta.persistence.EntityManager;

@SpringBootTest
@DataJpaTest
class FoodDAOHibernateJPATest {

  @Autowired
  private EntityManager em; // Inyección del EntityManager

  private FoodDAOHibernateJPA foodDAO;

  @BeforeEach
  void setUp() {
    foodDAO = new FoodDAOHibernateJPA();

    // Inicializa la base de datos
    Food food1 = new Food("Pizza", false, "Fast Food");
    Food food2 = new Food("Salad", false, "Vegetable");
    Food food3 = new Food("Vegan potato omelette", true, "Vegetable");

    em.persist(food1);
    em.persist(food2);
    em.persist(food3);
  }

  @AfterEach
  @Rollback // Revierte cambios después de cada prueba
  void tearDown() {
    em.createQuery("DELETE FROM Food").executeUpdate(); // Elimina todos los alimentos
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

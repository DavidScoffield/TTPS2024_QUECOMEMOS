package com.ttps.quecomemos.services;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttps.quecomemos.model.Food;
import com.ttps.quecomemos.repository.FoodRepository;

@SpringBootTest
// @ActiveProfiles("test") // Usar el perfil de prueba
public class FoodServiceTest {

  @Autowired
  private FoodRepository foodRepository;

  @Autowired
  private FoodService foodService;

  @AfterEach
  public void tearDown() {
    // Clear the test data after each test
    foodRepository.deleteAll();
  }

  @Test
  public void testGetByName() {
    Food food = new Food();
    food.setName("Pizza");
    foodRepository.save(food); // Guardar en la base de datos de pruebas

    Food result = foodService.findFoodByName("Pizza");

    assertNotNull(result);
    assertEquals("Pizza", result.getName());
  }

  @Test
  public void testGetByType() {
    Food food1 = new Food();
    food1.setType("Italian");
    Food food2 = new Food();
    food2.setType("Italian");
    foodRepository.saveAll(Arrays.asList(food1, food2)); // Guardar en la base de datos de pruebas

    List<Food> result = foodService.findFoodByType("Italian");

    assertEquals(2, result.size());
  }

  @Test
  public void testGetVegetarians() {
    Food food1 = new Food();
    food1.setIsVegetarian(true);
    Food food2 = new Food();
    food2.setIsVegetarian(true);
    foodRepository.saveAll(Arrays.asList(food1, food2)); // Guardar en la base de datos de pruebas

    List<Food> result = foodService.findFoodByIsVegetarian();

    assertEquals(2, result.size());
  }

  @Test
  public void testGetNonVegetarians() {
    Food food1 = new Food();
    food1.setIsVegetarian(false);
    Food food2 = new Food();
    food2.setIsVegetarian(false);
    foodRepository.saveAll(Arrays.asList(food1, food2));

    List<Food> result = foodService.findFoodByIsNotVegetarian();

    assertEquals(2, result.size());
  }
}

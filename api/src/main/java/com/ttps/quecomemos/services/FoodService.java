package com.ttps.quecomemos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ttps.quecomemos.dto.FoodRegisterDTO;
import com.ttps.quecomemos.model.Food;
import com.ttps.quecomemos.repository.FoodRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FoodService {

  @Autowired
  private FoodRepository foodRepository;

  public Food findFoodByName(String name) {
    return foodRepository.findByName(name);
  }

  public List<Food> findFoodByType(String type) {
    return foodRepository.findByType(type);
  }

  public Food getFoodById(Long id) {
    return foodRepository.findById(id).orElse(null);
  }

  public List<Food> findFoodByIsVegetarian() {
    return foodRepository.findByIsVegetarian(true);
  }

  public List<Food> findFoodByIsNotVegetarian() {
    return foodRepository.findByIsVegetarian(false);
  }

  public Food registerNewFood(FoodRegisterDTO foodRegisterDTO) {
    // Check if food exists
    Food existingFood = this.findFoodByName(foodRegisterDTO.getName());
    if (existingFood != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Food already exists");
    }

    Food newFood = new Food(foodRegisterDTO.getName(), foodRegisterDTO.getIsVegetarian(),
        foodRegisterDTO.getType().toString());

    foodRepository.save(newFood);

    log.info("Food registered successfully: {}", newFood);

    return newFood;
  }

  public Food updateFood(Long foodId, FoodRegisterDTO updateFoodDTO) {
    Optional<Food> optionalFood = foodRepository.findById(foodId);
    if (!optionalFood.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid food id");
    }
    Food existingFood = optionalFood.get();

    if (!updateFoodDTO.getName().isEmpty()) {
      existingFood.setName(updateFoodDTO.getName());
    }
    if (!updateFoodDTO.getType().toString().isEmpty()) {
      existingFood.setType(updateFoodDTO.getType().toString());
    }

    existingFood.setIsVegetarian(updateFoodDTO.getIsVegetarian());

    return foodRepository.save(existingFood);
  }

  public List<Food> getAllFoods() {
    return foodRepository.findAll();
  }

}

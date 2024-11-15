package com.ttps.quecomemos.services;

import java.util.List;

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
	    	foodRegisterDTO.getType());

	    foodRepository.save(newFood);

	    log.info("Food registered successfully: {}", newFood);

	    return newFood;
	  }
  
  public Food updateFood(String foodName, FoodRegisterDTO updateFoodDTO) {
	  	Food existingFood= this.findFoodByName(foodName);
	  	if (existingFood == null) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid food name");
	      }

	    existingFood.updateDetails(updateFoodDTO);

	    return foodRepository.save(existingFood);
  }
  
  public List<Food> getAllFoods (){
	  return foodRepository.findAll();
  }
  
  
}

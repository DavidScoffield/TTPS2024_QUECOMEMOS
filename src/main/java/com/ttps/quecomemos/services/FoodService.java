package com.ttps.quecomemos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttps.quecomemos.model.Food;
import com.ttps.quecomemos.repository.FoodRepository;

@Service
public class FoodService {

  @Autowired
  private FoodRepository foodRepository;

  public Food getByName(String name) {
    return foodRepository.findByName(name);
  }

  public List<Food> getByType(String type) {
    return foodRepository.findByType(type);
  }

  public List<Food> getVegetarians() {
    return foodRepository.findByIsVegetarian(true);
  }

  public List<Food> getNonVegetarians() {
    return foodRepository.findByIsVegetarian(false);
  }
}

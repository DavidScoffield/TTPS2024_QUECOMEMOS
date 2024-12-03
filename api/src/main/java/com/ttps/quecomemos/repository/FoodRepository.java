package com.ttps.quecomemos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.quecomemos.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

  Food findByName(String name);

  List<Food> findByType(String type);

  List<Food> findByIsVegetarian(Boolean isVegetarian);

}

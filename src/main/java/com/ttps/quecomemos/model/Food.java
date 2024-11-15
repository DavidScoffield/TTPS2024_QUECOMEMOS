package com.ttps.quecomemos.model;

import com.ttps.quecomemos.dto.FoodRegisterDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "food_id")
  private Long id;

  private String name;

  private Boolean isVegetarian;

  private String type;

  public Food(String name, Boolean isVegetarian, String type) {
    this.name = name;
    this.isVegetarian = isVegetarian;
    this.type = type;
  }
  
  @Override
  public String toString() {
    return "Food{name='" + getName() + "', type='" + getType() + "'}";
  }

  public void updateDetails(FoodRegisterDTO newFoodDTO) {
    if (!newFoodDTO.getName().isEmpty()) {
      setName(newFoodDTO.getName());
    }
    if (!newFoodDTO.getType().isEmpty()) {
      setType(newFoodDTO.getType());
    }
    
    setIsVegetarian(newFoodDTO.getIsVegetarian());

  }
}

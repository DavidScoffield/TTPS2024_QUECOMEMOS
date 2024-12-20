package com.ttps.quecomemos.dto;

import com.ttps.quecomemos.enums.FoodType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodRegisterDTO {

  @NotNull
  private String name;

  @NotNull
  private FoodType type;

  @NotNull
  private Boolean isVegetarian;

  public FoodRegisterDTO(String name, FoodType type, Boolean isVegetarian) {
    this.name = name;
    this.isVegetarian = isVegetarian;
    this.type = type;
  }

}

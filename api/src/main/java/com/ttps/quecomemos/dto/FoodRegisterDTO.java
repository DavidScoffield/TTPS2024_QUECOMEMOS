package com.ttps.quecomemos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodRegisterDTO {

  @NotNull
  private String name;

  @NotNull
  private String type;

  @NotNull
  private Boolean isVegetarian;
  
  public FoodRegisterDTO(String name, String type, Boolean isVegetarian) {
	  this.name= name;
	  this.isVegetarian = isVegetarian;
	  this.type= type;
  }

}

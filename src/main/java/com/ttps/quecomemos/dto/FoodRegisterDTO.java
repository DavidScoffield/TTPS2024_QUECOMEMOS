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

}

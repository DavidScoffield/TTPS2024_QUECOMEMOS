
package com.ttps.quecomemos.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FoodType {
  ENTRADA(1, "ENTRADA"), PLATO_PRINCIPAL(2, "PLATO PRINCIPAL"), POSTRE(3, "POSTRE"),
  BEBIDA(4, "BEBIDA"), OTRO(5, "OTRO");

  private final int id;
  private final String type;

  FoodType(int id, String type) {
    this.id = id;
    this.type = type;
  }

  public int id() {
    return id;
  }

  @Override
  public String toString() {
    return type;
  }

  @JsonCreator
  public static UserRole fromString(String value) {
    for (UserRole role : UserRole.values()) {
      if (role.name().equalsIgnoreCase(value)) {
        return role;
      }
    }
    throw new IllegalArgumentException(
        "Invalid value for food type: " + value + ". The valid values are: [" + ENTRADA
            + ", " + PLATO_PRINCIPAL + ", " + POSTRE + ", " + BEBIDA + ", " + OTRO + "]");
  }
}

package com.ttps.quecomemos.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
  ADMIN(1, "ROLE_ADMIN"), CLIENT(2, "ROLE_CLIENT"),
  SHIFT_MANAGER(3, "ROLE_SHIFT_MANAGER");

  private final int id;
  private final String role;

  UserRole(int id, String role) {
    this.id = id;
    this.role = role;
  }

  public int id() {
    return id;
  }

  @Override
  public String toString() {
    return role;
  }

  @JsonCreator
  public static UserRole fromString(String value) {
    for (UserRole role : UserRole.values()) {
      if (role.name().equalsIgnoreCase(value)) {
        return role;
      }
    }
    throw new IllegalArgumentException("Invalid value for UserRole: " + value
        + ". The valid values are: [" + UserRole.ADMIN + ", " + UserRole.CLIENT
        + ", " + UserRole.SHIFT_MANAGER + "]");
  }
}
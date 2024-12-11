package com.ttps.quecomemos.dto;

import com.ttps.quecomemos.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDTO {

  @NotNull
  private String dni;

  @NotNull
  private String password;

  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  private UserRole roleSelected;

  public UserRegisterDTO() {
  }

  public UserRegisterDTO(String dni, String password, String name, String email,
      UserRole roleSelected) {
    this.dni = dni;
    this.password = password;
    this.name = name;
    this.email = email;
    this.roleSelected = roleSelected;
  }

}

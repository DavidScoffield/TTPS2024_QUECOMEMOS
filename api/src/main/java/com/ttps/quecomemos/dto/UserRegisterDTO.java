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
}

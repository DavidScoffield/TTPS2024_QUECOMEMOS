package com.ttps.quecomemos.dto;

import lombok.Data;

@Data
public class UserWithoutPasswordDTO {

  private Long id;

  private String dni;

  private String name;

  private String email;

  private String role;

  public UserWithoutPasswordDTO(Long id, String dni, String name, String email,
      String role) {
    this.id = id;
    this.dni = dni;
    this.name = name;
    this.email = email;
    this.role = role;
  }
}

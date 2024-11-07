package com.ttps.quecomemos.dto;

import com.ttps.quecomemos.enums.UserRole;

import lombok.Data;

@Data
public class UserRegisterDTO {

  private String dni;
  private String password;
  private String repeatPassword;
  private String name;
  private String email;
  private UserRole roleSelected;
}

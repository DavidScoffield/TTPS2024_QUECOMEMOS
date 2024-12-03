package com.ttps.quecomemos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserDTO {

  @NotNull
  public String dni;

  @NotNull
  public String password;

}

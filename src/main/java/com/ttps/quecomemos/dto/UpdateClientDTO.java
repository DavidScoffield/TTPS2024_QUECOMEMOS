package com.ttps.quecomemos.dto;

import lombok.Data;

@Data
public class UpdateClientDTO {

  private String name;

  private String email;

  private String actualPassword;

  private String newPassword;

  private String photo;

}

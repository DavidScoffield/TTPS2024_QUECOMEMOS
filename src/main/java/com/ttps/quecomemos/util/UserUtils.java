package com.ttps.quecomemos.util;

import com.ttps.quecomemos.dto.UserRegisterDTO;
import com.ttps.quecomemos.errors.ValidationDataException;

public class UserUtils {
  public static boolean isRegistrationDataComplete(
      UserRegisterDTO userRegisterDTO) throws ValidationDataException {
    if (userRegisterDTO.getDni() == null) {
      throw new ValidationDataException("dni", "Missing `dni` field");
    }

    if (userRegisterDTO.getName() == null) {
      throw new ValidationDataException("name", "Missing `name` field");
    }

    if (userRegisterDTO.getEmail() == null) {
      throw new ValidationDataException("email", "Missing `email` field");
    }

    if (userRegisterDTO.getPassword() == null) {
      throw new ValidationDataException("password", "Missing `password` field");
    }

    if (userRegisterDTO.getRepeatPassword() == null) {
      throw new ValidationDataException("repeatPassword",
          "Missing `repeatPassword` field");
    }

    if (userRegisterDTO.getRoleSelected() == null) {
      throw new ValidationDataException("roleSelected",
          "Missing `roleSelected` field");
    }

    return true;

  }

}
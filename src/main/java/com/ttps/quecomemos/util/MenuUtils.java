package com.ttps.quecomemos.util;

import com.ttps.quecomemos.dto.MenuRegisterDTO;
import com.ttps.quecomemos.errors.ValidationDataException;

public class MenuUtils {
  public static boolean isDataComplete(MenuRegisterDTO menuRegisterDTO)
      throws ValidationDataException {
    if (menuRegisterDTO.getName() == null) {
      throw new ValidationDataException("name", "Missing `name` field");
    }

    if (menuRegisterDTO.getPrice() == null) {
      throw new ValidationDataException("price", "Missing `price` field");
    }

    if (menuRegisterDTO.getPrice() <= 0) {
      throw new ValidationDataException("price", "The price must be greater than 0");
    }

    if (menuRegisterDTO.getFoodsIds() == null || menuRegisterDTO.getFoodsIds().isEmpty()
        || menuRegisterDTO.getFoodsIds().contains(null)) {
      throw new ValidationDataException("foodIds",
          "The `foodIds` list is empty or missing. This field cannot contain null values");
    }

    return true;

  }
}

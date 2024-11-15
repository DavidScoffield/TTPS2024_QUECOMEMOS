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

	    if (menuRegisterDTO.getFoods() == null) {
	      throw new ValidationDataException("foods", "Missing `foods` field");
	    }

	    return true;

	}
}

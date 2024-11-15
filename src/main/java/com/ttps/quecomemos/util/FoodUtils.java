package com.ttps.quecomemos.util;

import com.ttps.quecomemos.dto.FoodRegisterDTO;
import com.ttps.quecomemos.errors.ValidationDataException;

public class FoodUtils {
	
	  public static boolean isDataComplete(FoodRegisterDTO foodRegisterDTO)
		      throws ValidationDataException {
		    if (foodRegisterDTO.getName() == null) {
		      throw new ValidationDataException("name", "Missing `name` field");
		    }

		    if (foodRegisterDTO.getType() == null) {
		      throw new ValidationDataException("type", "Missing `type` field");
		    }

		    if (foodRegisterDTO.getIsVegetarian() == null) {
		      throw new ValidationDataException("isVegetarian", "Missing `isVegetarian` field");
		    }

		    return true;

		  }
}

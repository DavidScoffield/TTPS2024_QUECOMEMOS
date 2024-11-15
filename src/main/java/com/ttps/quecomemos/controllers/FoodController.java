package com.ttps.quecomemos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttps.quecomemos.dto.FoodRegisterDTO;
import com.ttps.quecomemos.model.Food;
import com.ttps.quecomemos.services.FoodService;
import com.ttps.quecomemos.util.ApiResponseDTO;
import com.ttps.quecomemos.util.FoodUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/foods")
@Slf4j
@Tag(name = "Food Controller", description = "Operations related to foods")
public class FoodController {

  @Autowired
  private FoodService foodService;

  /**
   * Handles the registration of a new food.
   *
   * @param foodRegisterDTO The user object to be registered.
   * @return A ResponseEntity containing the registered food and an HTTP status code. - If
   *         the registration is successful, returns the registered food and HTTP status
   *         201 (Created). - If an error occurs, returns null and HTTP status 500
   *         (Internal Server Error).
   */
  @PostMapping("/register")
  @Operation(summary = "Register a new food", description = "Registers a new food in the system")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Food registered successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "409", description = "Food already exists", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<Food>> registerFood(@RequestBody
  FoodRegisterDTO foodRegisterDTO) {
    log.info("Registering food: {}", foodRegisterDTO);

    // Validate data
    FoodUtils.isDataComplete(foodRegisterDTO);

    Food newFood = foodService.registerNewFood(foodRegisterDTO);
    ApiResponseDTO<Food> response = new ApiResponseDTO<>(newFood,
        "Food registered successfully", HttpStatus.CREATED);
    return new ResponseEntity<>(response, HttpStatus.CREATED);

  }

  /**
   * Lists all foods in the system.
   *
   * @return A ResponseEntity containing a list of foods and an HTTP status code. - If the
   *         retrieval is successful, returns the list of foods and HTTP status 200 (OK).
   *         - If an error occurs, returns null and HTTP status 500 (Internal Server
   *         Error).
   */
  @GetMapping()
  @Operation(summary = "List all foods", description = "Retrieves a list of all foods in the system")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Foods retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<List<Food>>> getAllFoods() {
    log.info("Listing all foods");

    try {
      List<Food> foodList = foodService.getAllFoods();
      ApiResponseDTO<List<Food>> response = new ApiResponseDTO<>(foodList,
          "Foods retrieved successfully", HttpStatus.OK);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("An error occurred while retrieving foods", e);
      ApiResponseDTO<List<Food>> response = new ApiResponseDTO<>(null,
          "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Updates an existing food.
   * 
   * @param id      The id of the actual food to be updated.
   * @param updateFoodDTO The updated food object.
   * @return A ResponseEntity containing the updated food and an HTTP status code.
   */

  @PutMapping("/update/{id}")
  @Operation(summary = "Update Food", description = "Updates an existing food. Requires id of the food to be updated and the updated food object.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Food updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<Food>> updateFood(@PathVariable
  Long id, @RequestBody
  FoodRegisterDTO updateFoodDTO) {
    log.info("Updating food: {}", updateFoodDTO, " with id: {}", id);

    // Validate data
    FoodUtils.isDataComplete(updateFoodDTO);

    // Update food
    Food updatedFood = foodService.updateFood(id, updateFoodDTO);

    ApiResponseDTO<Food> response = new ApiResponseDTO<>(updatedFood,
        "Food updated successfully", HttpStatus.OK);
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleHttpMessageNotReadable(
      HttpMessageNotReadableException e) {
    log.error("Invalid Data Input provided: {}", e.getMessage());
    ApiResponseDTO<Void> response = new ApiResponseDTO<>(null, e.getMessage(),
        HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
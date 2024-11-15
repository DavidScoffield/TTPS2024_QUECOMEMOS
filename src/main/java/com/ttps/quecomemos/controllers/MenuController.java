package com.ttps.quecomemos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.ttps.quecomemos.dto.MenuRegisterDTO;
import com.ttps.quecomemos.model.Menu;
import com.ttps.quecomemos.services.MenuService;
import com.ttps.quecomemos.util.ApiResponseDTO;
import com.ttps.quecomemos.handlers.GenericExceptionHandler;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/menus")
@Slf4j
@Tag(name = "Menu Controller", description = "Operations related to menus")
public class MenuController {

  @Autowired
  private MenuService menuService;

  @Autowired
  private GenericExceptionHandler exceptionHandler;

  /**
   * Registers a new menu.
   *
   * @param menuRegisterDTO The menu object to be registered.
   * @return A ResponseEntity containing the registered menu and an HTTP status code.
   */
  @PostMapping("/register")
  @Operation(summary = "Register a new menu", description = "Registers a new menu in the system")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Menu registered successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "409", description = "Menu already exists", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<Menu>> registerMenu(@RequestBody MenuRegisterDTO menuRegisterDTO) {
    log.info("Registering menu: {}", menuRegisterDTO);

    try {
        Menu newMenu = menuService.registerNewMenu(menuRegisterDTO);
        ApiResponseDTO<Menu> response = new ApiResponseDTO<>(newMenu, "Menu registered successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
        log.error("Error registering menu", e);
        ApiResponseDTO<Menu> response = new ApiResponseDTO<>(null, "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Lists all menus in the system.
   *
   * @return A ResponseEntity containing a list of menus and an HTTP status code.
   */
  @GetMapping("/list")
  @Operation(summary = "List all menus", description = "Retrieves a list of all menus in the system")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<List<Menu>>> getAllMenus() {
      log.info("Listing all menus");

      try {
          List<Menu> menuList = menuService.getAllMenus();
          ApiResponseDTO<List<Menu>> response = new ApiResponseDTO<>(menuList, "Menus retrieved successfully", HttpStatus.OK);
          return new ResponseEntity<>(response, HttpStatus.OK);
      } catch (Exception e) {
          log.error("An error occurred while retrieving menus", e);
          ApiResponseDTO<List<Menu>> response = new ApiResponseDTO<>(null, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
          return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  /**
   * Updates an existing menu.
   *
   * @param menuName       The name of the menu to be updated.
   * @param updateMenuDTO The updated menu object.
   * @return A ResponseEntity containing the updated menu and an HTTP status code.
   */
  @PutMapping("/update/{menuName}")
  @Operation(summary = "Update Menu", description = "Updates an existing menu by name")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Menu updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<Menu>> updateMenu(@PathVariable String menuName, @RequestBody MenuRegisterDTO updateMenuDTO) {
    log.info("Updating menu with name: {}", menuName);

    try {
        Menu updatedMenu = menuService.updateMenu(menuName, updateMenuDTO);
        ApiResponseDTO<Menu> response = new ApiResponseDTO<>(updatedMenu, "Menu updated successfully", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        log.error("Error updating menu", e);
        ApiResponseDTO<Menu> response = new ApiResponseDTO<>(null, "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
    log.error("Invalid Data Input provided: {}", e.getMessage());
    ApiResponseDTO<Void> response = new ApiResponseDTO<>(null, e.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}

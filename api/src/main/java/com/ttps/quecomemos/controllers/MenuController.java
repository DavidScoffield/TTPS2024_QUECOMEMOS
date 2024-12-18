package com.ttps.quecomemos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttps.quecomemos.dto.ApiResponseDTO;
import com.ttps.quecomemos.dto.MenuRegisterDTO;
import com.ttps.quecomemos.model.Menu;
import com.ttps.quecomemos.services.MenuService;
import com.ttps.quecomemos.util.MenuUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/menus")
@Slf4j
@Tag(name = "Menu Controller", description = "Operations related to menus")
public class MenuController {

  @Autowired
  private MenuService menuService;

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
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content) })
  public ResponseEntity<ApiResponseDTO<Menu>> registerMenu(@RequestBody
  MenuRegisterDTO menuRegisterDTO) {
    log.info("Registering menu: {}", menuRegisterDTO);

    MenuUtils.isDataComplete(menuRegisterDTO);

    Menu newMenu = menuService.registerNewMenu(menuRegisterDTO);
    ApiResponseDTO<Menu> response = new ApiResponseDTO<>(newMenu,
        "Menu registered successfully", HttpStatus.CREATED);
    return new ResponseEntity<>(response, HttpStatus.CREATED);

  }

  /**
   * Lists all menus in the system.
   *
   * @return A ResponseEntity containing a list of menus and an HTTP status code.
   */
  @GetMapping()
  @Operation(summary = "List all menus", description = "Retrieves a list of all menus in the system")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content) })
  public ResponseEntity<ApiResponseDTO<List<Menu>>> getAllMenus() {
    log.info("Listing all menus");

    List<Menu> menuList = menuService.getAllMenus();
    ApiResponseDTO<List<Menu>> response = new ApiResponseDTO<>(menuList,
        "Menus retrieved successfully", HttpStatus.OK);
    return new ResponseEntity<>(response, HttpStatus.OK);

  }
  
  /**
   * Retrieves a menu by its ID.
   *
   * @param id The ID of the menu to retrieve.
   * @return A ResponseEntity containing the menu and an HTTP status code.
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get Menu by ID", description = "Retrieves a menu by its ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Menu retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<Menu>> getMenuById(@PathVariable Long id) {
      log.info("Fetching menu with id: {}", id);

      Menu menu = menuService.getMenuById(id);
      if (menu == null) {
          log.error("Menu with id {} not found", id);
          ApiResponseDTO<Menu> response = new ApiResponseDTO<>(null, "Menu not found", HttpStatus.NOT_FOUND);
          return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }

      ApiResponseDTO<Menu> response = new ApiResponseDTO<>(menu, "Menu retrieved successfully", HttpStatus.OK);
      return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  
  /**
   * Deletes a menu by its ID.
   *
   * @param id The ID of the menu to delete.
   * @return A ResponseEntity containing the result of the deletion and an HTTP status code.
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Menu by ID", description = "Deletes a menu by its ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Menu deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Menu not found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<Void>> deleteMenu(@PathVariable Long id) {
      log.info("Deleting menu with id: {}", id);

      boolean isDeleted = menuService.deleteMenuById(id);
      if (!isDeleted) {
          log.error("Menu with id {} not found", id);
          ApiResponseDTO<Void> response = new ApiResponseDTO<>(null, "Menu not found", HttpStatus.NOT_FOUND);
          return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }

      ApiResponseDTO<Void> response = new ApiResponseDTO<>(null, "Menu deleted successfully", HttpStatus.OK);
      return new ResponseEntity<>(response, HttpStatus.OK);
  }


  /**
   * Updates an existing menu.
   *
   * @param id            The id of the menu to be updated.
   * @param updateMenuDTO The updated menu object.
   * @return A ResponseEntity containing the updated menu and an HTTP status code.
   */
  @PutMapping("/update/{id}")
  @Operation(summary = "Update Menu", description = "Updates an existing menu by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Menu updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content) })
  public ResponseEntity<ApiResponseDTO<Menu>> updateMenu(@PathVariable
  Long id, @RequestBody
  MenuRegisterDTO updateMenuDTO) {
    log.info("Updating menu with id: {}", id);

    MenuUtils.isDataComplete(updateMenuDTO);

    Menu updatedMenu = menuService.updateMenu(id, updateMenuDTO);
    ApiResponseDTO<Menu> response = new ApiResponseDTO<>(updatedMenu,
        "Menu updated successfully", HttpStatus.OK);
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

package com.ttps.quecomemos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ttps.quecomemos.dto.UserRegisterDTO;
import com.ttps.quecomemos.enums.UserRole;
import com.ttps.quecomemos.errors.ValidationDataException;
import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.ShoppingCart;
import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.services.UserService;
import com.ttps.quecomemos.util.ApiResponseDTO;
import com.ttps.quecomemos.util.UserUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Tag(name = "User Controller", description = "Operations related to users")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Handles the registration of a new user.
   *
   * @param userRegisterDTO The user object to be registered.
   * @return A ResponseEntity containing the registered user and an HTTP status code. - If
   *         the registration is successful, returns the registered user and HTTP status
   *         201 (Created). - If an error occurs, returns null and HTTP status 500
   *         (Internal Server Error).
   */
  @PostMapping("/register")
  @Operation(summary = "Register a new user", description = "Registers a new user in the system")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "User registered successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "409", description = "User already exists", content = @Content),
      @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)
  })
  public ResponseEntity<ApiResponseDTO<User>> registerUser(@RequestBody
  UserRegisterDTO userRegisterDTO) {
    log.info("Registering user: {}", userRegisterDTO);

    try {
      // Validate data
      UserUtils.isRegistrationDataComplete(userRegisterDTO);

      if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepeatPassword())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Passwords do not match");
      }

      // Check if user already exists
      User existingUser = userService.findUserByDNI(userRegisterDTO.getDni());

      if (existingUser != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            String.format("User with `dni` %s already exists", userRegisterDTO.getDni()));
      }

      // Register new user
      User newUser;

      if (userRegisterDTO.getRoleSelected() == UserRole.CLIENT) {
        // Create client and associated shopping cart
        Client newClient = new Client(userRegisterDTO.getDni(), userRegisterDTO.getName(),
            userRegisterDTO.getEmail(), userRegisterDTO.getPassword(),
            userRegisterDTO.getRoleSelected().toString());

        ShoppingCart newCart = new ShoppingCart(newClient);
        newClient.setCart(newCart);

        newUser = userService.registerClient(newClient);
        log.info("Client registered successfully: {}", (Client) newUser);

      } else {
        // Create regular user
        newUser = new User(userRegisterDTO.getDni(), userRegisterDTO.getName(),
            userRegisterDTO.getEmail(), userRegisterDTO.getPassword(),
            userRegisterDTO.getRoleSelected().toString());

        newUser = userService.registerUser(newUser);
        log.info("User registered successfully: {}", newUser);
      }

      ApiResponseDTO<User> response = new ApiResponseDTO<>(newUser,
          "User registered successfully", HttpStatus.CREATED);
      return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (ValidationDataException e) {
      log.error("Validation error: {}", e.getMessage());
      return handleValidationException(e);
    } catch (ResponseStatusException e) {
      log.error("Status error: {}", e.getMessage());
      ApiResponseDTO<User> response = new ApiResponseDTO<>(null, e.getReason(),
          HttpStatus.valueOf(e.getStatusCode().value()));
      return new ResponseEntity<>(response, e.getStatusCode());
    } catch (DataIntegrityViolationException e) {
      log.error("Data integrity violation: {}", e.getMessage());
      ApiResponseDTO<User> response = new ApiResponseDTO<>(null, "User already exists",
          HttpStatus.CONFLICT);
      return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    } catch (Exception e) {
      log.error("Unexpected error registering user", e);
      ApiResponseDTO<User> response = new ApiResponseDTO<>(null,
          "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Errors handlers
  @ExceptionHandler(ValidationDataException.class)
  private ResponseEntity<ApiResponseDTO<User>> handleValidationException(
      ValidationDataException e) {
    ApiResponseDTO<User> response = new ApiResponseDTO<>(null, e.getMessage(),
        HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleHttpMessageNotReadable(
      HttpMessageNotReadableException e) {
    String errorMessage = "Invalid input for `roleSelected`. Accepted values are: SHIFT_MANAGER, CLIENT, ADMIN.";
    log.error("Invalid UserRole provided: {}", e.getMessage());
    ApiResponseDTO<Void> response = new ApiResponseDTO<>(null, errorMessage,
        HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleGenericException(Exception e) {
    log.error("Unexpected error: {}", e.getMessage());
    ApiResponseDTO<Void> response = new ApiResponseDTO<>(null,
        "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

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
import com.ttps.quecomemos.util.ApiResponse;
import com.ttps.quecomemos.util.UserUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
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
  public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody
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

      ApiResponse<User> response = new ApiResponse<>(newUser,
          "User registered successfully", HttpStatus.CREATED);
      return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (ValidationDataException e) {
      log.error("Validation error: {}", e.getMessage());
      return handleValidationException(e);
    } catch (ResponseStatusException e) {
      log.error("Status error: {}", e.getMessage());
      ApiResponse<User> response = new ApiResponse<>(null, e.getReason(),
          HttpStatus.valueOf(e.getStatusCode().value()));
      return new ResponseEntity<>(response, e.getStatusCode());
    } catch (DataIntegrityViolationException e) {
      log.error("Data integrity violation: {}", e.getMessage());
      ApiResponse<User> response = new ApiResponse<>(null, "User already exists",
          HttpStatus.CONFLICT);
      return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    } catch (Exception e) {
      log.error("Unexpected error registering user", e);
      ApiResponse<User> response = new ApiResponse<>(null, "Unexpected error occurred",
          HttpStatus.INTERNAL_SERVER_ERROR);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Errors handlers
  @ExceptionHandler(ValidationDataException.class)
  private ResponseEntity<ApiResponse<User>> handleValidationException(
      ValidationDataException e) {
    ApiResponse<User> response = new ApiResponse<>(null, e.getMessage(),
        HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(
      HttpMessageNotReadableException e) {
    String errorMessage = "Invalid input for `roleSelected`. Accepted values are: SHIFT_MANAGER, CLIENT, ADMIN.";
    log.error("Invalid UserRole provided: {}", e.getMessage());
    ApiResponse<Void> response = new ApiResponse<>(null, errorMessage,
        HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception e) {
    log.error("Unexpected error: {}", e.getMessage());
    ApiResponse<Void> response = new ApiResponse<>(null, "Unexpected error occurred",
        HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

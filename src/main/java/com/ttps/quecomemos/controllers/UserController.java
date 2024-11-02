package com.ttps.quecomemos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.services.UserService;
import com.ttps.quecomemos.util.ApiResponse;

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
   * @param user The user object to be registered.
   * @return A ResponseEntity containing the registered user and an HTTP status code. - If
   *         the registration is successful, returns the registered user and HTTP status 201
   *         (Created). - If an error occurs, returns null and HTTP status 500 (Internal
   *         Server Error).
   */
  @PostMapping("/register")
  public ResponseEntity<ApiResponse<User>> registrarUsuario(@RequestBody
  User user) {
    log.info("Registering user: {}", user);

    try {
      // Validate data
      if (user.getDni() == null || user.getName() == null
          || user.getEmail() == null || user.getPassword() == null
          || user.getRole() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Missing required user data");
      }

      // Check if user already exists
      User existingUser = userService.findUserByDNI(user.getDni());

      if (existingUser != null) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            "User already exists");
      }

      // Guardar nuevo usuario
      User newUser = userService.save(user);
      log.info("User registered successfully: {}", newUser);

      ApiResponse<User> response = new ApiResponse<>(newUser,
          "User registered successfully", HttpStatus.CREATED);
      return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (ResponseStatusException e) {
      log.error("Error registering user: {}", e.getMessage());
      ApiResponse<User> response = new ApiResponse<>(null, e.getReason(),
          HttpStatus.valueOf(e.getStatusCode().value()));
      return new ResponseEntity<>(response, e.getStatusCode());

    } catch (Exception e) {
      log.error("Unexpected error registering user", e);
      ApiResponse<User> response = new ApiResponse<>(null,
          "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @ExceptionHandler
  public ResponseEntity<String> handleException(Exception e) {
    return new ResponseEntity<>(e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

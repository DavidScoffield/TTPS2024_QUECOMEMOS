package com.ttps.quecomemos.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ttps.quecomemos.errors.ValidationDataException;
import com.ttps.quecomemos.util.ApiResponseDTO;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler {

  @ExceptionHandler(ValidationDataException.class)
  public ResponseEntity<ApiResponseDTO<?>> handleValidationException(
      ValidationDataException e) {
    ApiResponseDTO<?> response = new ApiResponseDTO<>(null, e.getMessage(),
        HttpStatus.BAD_REQUEST);
    log.error("Validation error: {}", e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponseDTO<?>> handleResponseStatusException(
      ResponseStatusException e) {
    ApiResponseDTO<?> response = new ApiResponseDTO<>(null, e.getReason(),
        HttpStatus.valueOf(e.getStatusCode().value()));
    log.error("Status error: {}", e.getMessage());
    return new ResponseEntity<>(response, e.getStatusCode());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleHttpMessageNotReadable(
      HttpMessageNotReadableException e) {
    String errorMessage = "Invalid input data. Please check your request.";
    ApiResponseDTO<Void> response = new ApiResponseDTO<>(null, errorMessage,
        HttpStatus.BAD_REQUEST);
    log.error("Invalid input data: {}", e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleNoResourceFoundException(
      NoResourceFoundException e) {
    String message = e.getMessage();

    log.error("Resource not found: {}", message);
    ApiResponseDTO<Void> response = new ApiResponseDTO<>(null,
        "Resource not found for: " + message, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleGenericException(Exception e) {
    log.error("Unexpected error: {}", e.getMessage());
    ApiResponseDTO<Void> response = new ApiResponseDTO<>(null,
        "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

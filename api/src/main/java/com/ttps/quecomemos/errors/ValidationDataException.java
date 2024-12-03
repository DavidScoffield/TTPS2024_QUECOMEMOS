package com.ttps.quecomemos.errors;

import lombok.Getter;

@Getter
public class ValidationDataException extends RuntimeException {

  private final String field; // Campo que tiene el error
  private final String errorCode; // Código de error específico, si aplica

  // Constructor principal
  public ValidationDataException(String field, String errorMessage,
      String errorCode) {
    super(errorMessage); // Mensaje de error de la excepción
    this.field = field;
    this.errorCode = errorCode;
  }

  // Constructor sin código de error
  public ValidationDataException(String field, String errorMessage) {
    super(errorMessage);
    this.field = field;
    this.errorCode = "UNKNOWN"; // Código de error por defecto
  }
}

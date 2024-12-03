package com.ttps.quecomemos.util;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Generic API response wrapper")
public class ApiResponseDTO<T> {

  @Schema(description = "The data returned in the response")
  private T data;

  @Schema(description = "Message describing the response")
  private String message;

  @Schema(description = "HTTP status code of the response", example = "201")
  private HttpStatus status;

}
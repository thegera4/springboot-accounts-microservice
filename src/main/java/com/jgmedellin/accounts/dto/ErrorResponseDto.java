package com.jgmedellin.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Schema(name = "ErrorResponse", description = "Schema to hold the error response")
@Data @AllArgsConstructor
public class ErrorResponseDto {

  @Schema(description = "API path where the error occurred")
  private String apiPath;

  @Schema(description = "HTTP status code in the response")
  private HttpStatus errorCode;

  @Schema(description = "Error message in the response")
  private String errorMessage;

  @Schema(description = "Time when the error occurred")
  private LocalDateTime errorTime;
}
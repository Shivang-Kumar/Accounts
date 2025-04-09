package com.myproject.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Schema to hold error response information.", name = "ErrorResponse")

public class ErrorResponseDto {
	@Schema(description = "API path invoked by client.")

	private String apiPath;
	@Schema(description = "Error Code representing the error.")

	private HttpStatus errorCode;
	@Schema(description = "Error Message representing the error.")

	private String errorMessage;
	@Schema(description = "Time representing the error happend.")
	private LocalDateTime errorTime;
}

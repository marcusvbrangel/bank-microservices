package com.bank.accounts.dto;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public record ResponseErrorDto(String apiPath, HttpStatusCode statusCode, String errorMessage, LocalDateTime timestamp) {
}

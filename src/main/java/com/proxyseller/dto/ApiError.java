package com.proxyseller.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(LocalDateTime timestamp,
                       String message,
                       HttpStatus status) {
}

package com.stadion.stadion_backend.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private final LocalDateTime thrownAt = LocalDateTime.now();
}

package com.stadion.stadion_backend.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String message;
    private final LocalDateTime thrownAt = LocalDateTime.now();
}

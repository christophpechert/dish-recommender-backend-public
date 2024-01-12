package com.project.dishrecommender.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Getter
public class ErrorDetails {

    private final LocalDateTime timestamp;
    private final String message;
    private final String details;
}

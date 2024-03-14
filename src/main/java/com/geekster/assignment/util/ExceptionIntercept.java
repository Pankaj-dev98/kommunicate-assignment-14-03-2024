package com.geekster.assignment.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionIntercept {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> invalidInputHandler(MethodArgumentNotValidException e) {
        var errors  = e.getBindingResult().getAllErrors();
        final StringBuilder build = new StringBuilder();

        errors.forEach(error -> {
            String field = ((FieldError)error).getField();
            String message = error.getDefaultMessage();

            build.append("Invalid input in field: %-10s Error message: %s".formatted(field, message));
        });

        Response response = Response.builder()
                .text(build.toString())
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .createdAt(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest()
                .body(response);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> invalidPrompt(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(
                        new Response(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value())
                );
    }
}

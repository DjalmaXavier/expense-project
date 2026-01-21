package com.dx.expense.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dx.expense.dto.ErrorResponseDTO;
import com.dx.expense.exception.EmailAlreadyExistException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailExists(
            EmailAlreadyExistException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        "EMAIL_ALREADY_EXISTS",
                        ex.getMessage()));
    }
}

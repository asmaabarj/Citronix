package com.projet.citronix.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FermeException.class)
    public ResponseEntity<ErrorResponse> handleFermeException(FermeException ex) {
        log.error("Erreur Ferme: {}", ex.getMessage());
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChampException.class)
    public ResponseEntity<ErrorResponse> handleChampException(ChampException ex) {
        log.error("Erreur Champ: {}", ex.getMessage());
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArbreException.class)
    public ResponseEntity<ErrorResponse> handleArbreException(ArbreException ex) {
        log.error("Erreur Arbre: {}", ex.getMessage());
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Entité non trouvée: {}", ex.getMessage());
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Erreur de validation: {}", errors);
        return createErrorResponse(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        log.error("Erreur inattendue: ", ex);
        return createErrorResponse("Une erreur interne est survenue", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecolteException.class)
    public ResponseEntity<ErrorResponse> handleRecolteException(RecolteException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DetailRecolteException.class)
    public ResponseEntity<ErrorResponse> handleDetailRecolteException(DetailRecolteException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(
            status.value(),
            message,
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private int status;
        private String message;
        private LocalDateTime timestamp;
    }
} 
package com.br.fiap.fortaleza.sabor.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(fieldError -> Objects.requireNonNull(fieldError.getDefaultMessage()), Collectors.toList())
                ));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> handleUserNotFoundException(UserNotFoundException exception) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("message", List.of(exception.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorMessage> handleGenericException(Exception ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiErrorMessage> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of("Access Denied"));
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.FORBIDDEN, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(UserCredentialsException.class)
    public ResponseEntity<ApiErrorMessage> handleUserCredentialsException(UserCredentialsException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of("Invalid email or password"));
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNAUTHORIZED, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorMessage> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of("User already registered"));
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<ApiErrorMessage> handleRestaurantAlreadyExistsException(RestaurantAlreadyExistsException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of("Restaurant already exists"));
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of("Restaurant not found"));
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(UserTypeMismatchException.class)
    public ResponseEntity<ApiErrorMessage> handleUserTypeMismatchException(UserTypeMismatchException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of("User type mismatch"));
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(MenuAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorMessage> handleMenuAlreadyRegisteredException(MenuAlreadyRegisteredException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of("Menu already registered"));
        errors.put("message", List.of(ex.getMessage()));

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }
}
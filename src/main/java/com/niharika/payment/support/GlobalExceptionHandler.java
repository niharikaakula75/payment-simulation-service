package com.niharika.payment.support;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> notFound(NotFoundException ex, HttpServletRequest req) {
    return build(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), req.getRequestURI());
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ApiError> badRequest(ValidationException ex, HttpServletRequest req) {
    return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", ex.getMessage(), req.getRequestURI());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  public ResponseEntity<ApiError> validation(Exception ex, HttpServletRequest req) {
    return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Invalid request payload", req.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Unexpected error occurred", req.getRequestURI());
  }

  private ResponseEntity<ApiError> build(HttpStatus status, String code, String message, String path) {
    ApiError err = new ApiError();
    err.error = code;
    err.message = message;
    err.path = path;
    return ResponseEntity.status(status).body(err);
  }
}

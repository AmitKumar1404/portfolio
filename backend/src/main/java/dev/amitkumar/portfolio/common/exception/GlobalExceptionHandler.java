package dev.amitkumar.portfolio.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import dev.amitkumar.portfolio.common.api.ApiError;
import dev.amitkumar.portfolio.github.GithubUnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage(), request, Map.of());
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ApiError> handleRateLimited(RateLimitExceededException ex, HttpServletRequest request) {
        return build(HttpStatus.TOO_MANY_REQUESTS, "RATE_LIMIT_EXCEEDED", ex.getMessage(), request, Map.of());
    }

    @ExceptionHandler(GithubUnavailableException.class)
    public ResponseEntity<ApiError> handleGithubUnavailable(GithubUnavailableException ex, HttpServletRequest request) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, "GITHUB_UNAVAILABLE", ex.getMessage(), request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed", request, fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                fieldErrors.putIfAbsent(violation.getPropertyPath().toString(), violation.getMessage()));
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed", request, fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.debug("Unreadable request body on {}", request.getRequestURI(), ex);
        return build(HttpStatus.BAD_REQUEST, "MALFORMED_REQUEST", "Request body is missing or malformed", request, Map.of());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleMissingResource(NoResourceFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", "The requested resource does not exist", request, Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled error on {}", request.getRequestURI(), ex);
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                request,
                Map.of());
    }

    private ResponseEntity<ApiError> build(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request,
            Map<String, String> fieldErrors) {
        ApiError body = ApiError.of(status.value(), status.getReasonPhrase(), code, message, request.getRequestURI(), fieldErrors);
        return ResponseEntity.status(status).body(body);
    }
}

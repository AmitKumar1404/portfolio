package dev.amitkumar.portfolio.common.api;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String code,
        String message,
        String path,
        Map<String, String> fieldErrors) {

    public static ApiError of(int status, String error, String code, String message, String path) {
        return new ApiError(Instant.now(), status, error, code, message, path, Map.of());
    }

    public static ApiError of(
            int status,
            String error,
            String code,
            String message,
            String path,
            Map<String, String> fieldErrors) {
        return new ApiError(
                Instant.now(),
                status,
                error,
                code,
                message,
                path,
                fieldErrors == null ? Map.of() : Map.copyOf(fieldErrors));
    }
}

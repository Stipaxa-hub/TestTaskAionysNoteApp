package com.task.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Whether the request was successful", example = "true")
    private boolean success;

    @Schema(description = "Human-readable message", example = "Note created successfully")
    private String message;

    @Schema(description = "Response payload")
    private T data;

    @Schema(description = "Timestamp of the response", example = "2026-04-08T19:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Request path", example = "/notes/1")
    private String path;

    private ApiResponse(boolean success, String message, T data, String path) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return new ApiResponse<>(true, message, data, path);
    }

    public static <T> ApiResponse<T> error(String message, T errorData, String path) {
        return new ApiResponse<>(false, message, errorData, path);
    }
}

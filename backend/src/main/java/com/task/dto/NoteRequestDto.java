package com.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating or updating a note")
public record NoteRequestDto(
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be 255 characters or less")
        @Schema(description = "Title of the note", example = "Shopping List", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @NotBlank(message = "Content is required")
        @Size(max = 10000, message = "Content must be 10000 characters or less")
        @Schema(description = "Content body of the note", example = "Milk, eggs, bread", requiredMode = Schema.RequiredMode.REQUIRED)
        String content
) {
}


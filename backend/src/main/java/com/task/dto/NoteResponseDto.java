package com.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Response payload representing a note")
public record NoteResponseDto(
        @Schema(description = "ID of the note", example = "1")
        Long id,
        @Schema(description = "Title of the note", example = "Shopping List")
        String title,
        @Schema(description = "Content body of the note", example = "Milk, eggs, bread")
        String content,
        @Schema(description = "Timestamp when the note was created", example = "2026-04-08T19:30:00")
        LocalDateTime createdAt,
        @Schema(description = "Timestamp when the note was last updated", example = "2026-04-08T20:15:00")
        LocalDateTime updatedAt
) {
}

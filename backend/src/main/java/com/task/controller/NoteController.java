package com.task.controller;

import com.task.dto.NoteRequestDto;
import com.task.dto.NoteResponseDto;
import com.task.response.ApiResponse;
import com.task.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Endpoints for managing notes")
public class NoteController {
    private final NoteService noteService;

    @Operation(summary = "Get all notes", description = "Returns a paginated list of all notes, sorted by creation date by default")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<NoteResponseDto>>> getAllNotes(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable,
            HttpServletRequest request) {
        Page<NoteResponseDto> notes = noteService.getAllNotes(pageable);
        return ResponseEntity.ok(
                ApiResponse.success(notes, "Notes was received successfully", request.getRequestURI()));
    }

    @Operation(summary = "Get note by ID", description = "Returns a note by ID")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note was found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Note wasn't found", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> getNoteById(
            @Parameter(description = "ID of the note to receive", required = true)
            @PathVariable Long id, HttpServletRequest request) {
        NoteResponseDto noteResponseDto = noteService.getNoteById(id);
        return ResponseEntity.ok(
                ApiResponse.success(noteResponseDto, "Note was received successfully", request.getRequestURI())
        );
    }

    @Operation(summary = "Create a new note", description = "Creates a new note and returns the created resource")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Note was created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation was failed", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponse<NoteResponseDto>> createNote(@Valid @RequestBody NoteRequestDto requestDto, HttpServletRequest request) {
        NoteResponseDto createdNote = noteService.createNote(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(createdNote, "Note was created successfully", request.getRequestURI())
        );
    }

    @Operation(summary = "Update an existing note", description = "Updates the title and/or content of an existing note")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Note wasn't found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> updateNote(
            @Parameter(description = "ID of the note to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDto requestDto,
            HttpServletRequest request) {
        NoteResponseDto updated = noteService.updateNote(id, requestDto);
        return ResponseEntity.ok(
                ApiResponse.success(updated, "Note updated successfully", request.getRequestURI()));
    }

    @Operation(summary = "Delete a note", description = "Deletes a note by ID")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Note was deleted successfully", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Note wasn't found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @Parameter(description = "ID of the note to delete", required = true)
            @PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.task.dto.NoteRequestDto;
import com.task.dto.NoteResponseDto;
import com.task.mapper.NoteMapper;
import com.task.model.Note;
import com.task.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteService(noteRepository, noteMapper);
    }

    @Test
    void createNote_shouldMapSaveAndReturnDto() {
        NoteRequestDto requestDto = new NoteRequestDto("Title", "Content");
        Note note = new Note();
        Note saved = new Note();
        NoteResponseDto responseDto = new NoteResponseDto(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now());

        when(noteMapper.toNote(requestDto)).thenReturn(note);
        when(noteRepository.save(note)).thenReturn(saved);
        when(noteMapper.toResponseDto(saved)).thenReturn(responseDto);

        NoteResponseDto result = noteService.createNote(requestDto);

        assertEquals(1L, result.id());
        assertEquals("Title", result.title());
        verify(noteRepository).save(note);
    }

    @Test
    void getNote_shouldReturnExistNote() {
        Long id = 10L;
        Note note = new Note();
        NoteResponseDto responseDto = new NoteResponseDto(id, "Title", "Content", LocalDateTime.now(), LocalDateTime.now());

        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(noteMapper.toResponseDto(note)).thenReturn(responseDto);

        NoteResponseDto result = noteService.getNoteById(id);

        assertEquals(id, result.id());
        assertEquals("Title", result.title());
    }

    @Test
    void updateNote_shouldThrowWhenNoteNotFound() {
        Long missingId = 99L;
        NoteRequestDto requestDto = new NoteRequestDto("Updated", "Updated content");
        when(noteRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> noteService.updateNote(missingId, requestDto));

        verify(noteMapper, never()).updateNoteFromDto(any(), any());
        verify(noteRepository, never()).save(any());
    }

    @Test
    void deleteNoteById_shouldDeleteWhenFound() {
        Long id = 10L;
        Note note = new Note();
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        noteService.deleteNoteById(id);

        verify(noteRepository).delete(note);
    }
}

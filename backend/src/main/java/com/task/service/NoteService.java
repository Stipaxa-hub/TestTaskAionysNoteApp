package com.task.service;

import com.task.dto.NoteRequestDto;
import com.task.dto.NoteResponseDto;
import com.task.mapper.NoteMapper;
import com.task.model.Note;
import com.task.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public Page<NoteResponseDto> getAllNotes(Pageable pageable) {
        return noteRepository.findAll(pageable)
                .map(noteMapper::toResponseDto);
    }

    public NoteResponseDto getNoteById(Long id) {
        Note note = findNoteById(id);
        return noteMapper.toResponseDto(note);
    }

    @Transactional
    public NoteResponseDto createNote(NoteRequestDto noteRequestDto) {
        Note note = noteMapper.toNote(noteRequestDto);
        return noteMapper.toResponseDto(noteRepository.save(note));
    }

    @Transactional
    public NoteResponseDto updateNote(Long id, NoteRequestDto requestDto) {
        Note note = findNoteById(id);
        noteMapper.updateNoteFromDto(requestDto, note);
        return noteMapper.toResponseDto(noteRepository.save(note));
    }

    @Transactional
    public void deleteNoteById(Long id) {
        noteRepository.delete(findNoteById(id));
    }

    private Note findNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id: " + id));
    }
}

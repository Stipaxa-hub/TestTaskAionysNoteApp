package com.task.mapper;

import com.task.dto.NoteRequestDto;
import com.task.dto.NoteResponseDto;
import com.task.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Note toNote(NoteRequestDto noteRequestDto);

    NoteResponseDto toResponseDto(Note note);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateNoteFromDto(NoteRequestDto requestDto, @MappingTarget Note note);
}

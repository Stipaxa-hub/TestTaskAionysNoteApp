package com.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.config.WebConfig;
import com.task.dto.NoteRequestDto;
import com.task.dto.NoteResponseDto;
import com.task.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = NoteController.class)
@Import(WebConfig.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NoteService noteService;

    @Test
    void getAllNotes_shouldReturn200AndWrappedResponse() throws Exception {
        NoteResponseDto dto = new NoteResponseDto(1L, "T", "C", LocalDateTime.now(), LocalDateTime.now());
        when(noteService.getAllNotes(any(Pageable.class))).thenReturn(new PageImpl<>(java.util.List.of(dto)));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].id").value(1));
    }

    @Test
    void createNote_shouldReturn201() throws Exception {
        NoteRequestDto requestDto = new NoteRequestDto("Title", "Content");
        NoteResponseDto responseDto = new NoteResponseDto(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now());
        when(noteService.createNote(any(NoteRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Title"));
    }

    @Test
    void deleteNote_shouldReturn204() throws Exception {
        doNothing().when(noteService).deleteNoteById(eq(1L));

        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isNoContent());
    }
}

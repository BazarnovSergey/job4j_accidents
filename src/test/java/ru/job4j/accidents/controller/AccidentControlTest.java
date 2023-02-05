package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.job4j.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControlTest {

    @MockBean
    private AccidentService accidents;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenCreateAccidentThenReturnViewCreateAccident() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void whenCreateAccident() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("photo", null, "application", new byte[0]);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/saveAccident")
                        .file("file", mockMultipartFile.getBytes())
                        .param("name", "name")
                        .param("text", "text"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).createAccident(argument.capture(), any(), any());
        assertThat(argument.getValue().getName()).isEqualTo("name");
        assertThat(argument.getValue().getText()).isEqualTo("text");
    }

    @Test
    @WithMockUser
    public void whenUpdateAccident() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("photo", null, "application", new byte[0]);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/updateAccident")
                        .file("file", mockMultipartFile.getBytes())
                        .param("name", "name")
                        .param("text", "text")
                        .param("type.name", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidents).updateAccident(anyInt(), argument.capture(), any(), any());
        assertThat(argument.getValue().getName()).isEqualTo("name");
        assertThat(argument.getValue().getText()).isEqualTo("text");
        assertThat(argument.getValue().getType().getName()).isEqualTo("2");
    }

}
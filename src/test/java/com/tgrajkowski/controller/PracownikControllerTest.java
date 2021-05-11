package com.tgrajkowski.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tgrajkowski.model.pracownik.PracownikDto;
import com.tgrajkowski.service.PracownikService;
import com.tgrajkowski.utils.LocalDateAdapter;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PracownikController.class)
class PracownikControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PracownikService pracownikService;
    private String url = "/pracownik/";

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Test
    void findAll() throws Exception {
        List<PracownikDto> pracownikDtoList = new ArrayList<>();
        pracownikDtoList.add(PracownikDto.builder().id(1L).name("pracownik").build());
        pracownikDtoList.add(PracownikDto.builder().id(2L).name("pracownik2").build());
        Mockito.when(pracownikService.findAll()).thenReturn(pracownikDtoList);

        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].name", is("pracownik")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].name", is("pracownik2")))
        ;

    }

    @Test
    void createPracownik() throws Exception {
        PracownikDto pracownikDto1 = PracownikDto.builder().name("pracownik").build();
        PracownikDto pracownikSaved = PracownikDto.builder().id(4L).name("pracownik").build();
        Mockito.when(pracownikService.savePracownik(pracownikDto1)).thenReturn(pracownikSaved);

        String jsonContent = gson.toJson(pracownikDto1);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("pracownik")))
        ;
    }

    @Test
    void createPracownikInValidFields() throws Exception {
        PracownikDto pracownikDto1 = PracownikDto.builder()
                .build();

        String jsonContent = gson.toJson(pracownikDto1);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.exceptions.name[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.name[0].message", is("must not be empty")));
    }


    @Test
    void updatePracownik() throws Exception {
        long id = 4L;
        PracownikDto pracownikDto1 = PracownikDto.builder().name("pracownikUpdated").build();
        PracownikDto pracownikSaved = PracownikDto.builder().id(id).name("pracownikUpdated").build();
        Mockito.when(pracownikService.updatePracownik(id, pracownikDto1)).thenReturn(pracownikSaved);

        String jsonContent = gson.toJson(pracownikDto1);

        mockMvc.perform(put(url + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("pracownikUpdated")))
        ;
    }


    @Test
    void updatePracownikInValidFields() throws Exception {
        long id = 4L;

        PracownikDto pracownikDto1 = PracownikDto.builder()
                .build();

        String jsonContent = gson.toJson(pracownikDto1);

        mockMvc.perform(put(url + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.exceptions.name[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.name[0].message", is("must not be empty")))


        ;
    }

    @Test
    void deletePracownik() throws Exception {
        long id = 4L;
        Mockito.doNothing().when(pracownikService).deletePracownik(id);

        mockMvc.perform(delete(url + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
        ;
    }
}

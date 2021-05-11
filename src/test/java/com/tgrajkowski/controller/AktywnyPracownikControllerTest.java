package com.tgrajkowski.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikDto;
import com.tgrajkowski.service.AktywnyPracownikService;
import com.tgrajkowski.utils.LocalDateAdapter;
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
@WebMvcTest(AktywnyPracownikController.class)
class AktywnyPracownikControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AktywnyPracownikService aktywnyPracownikService;
    private String url = "/pracownik/aktywny/";
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Test
    void findAll() throws Exception {
        List<AktywnyPracownikDto> pracownikDtoList = new ArrayList<>();
        LocalDate firstDate = LocalDate.of(2021, 5, 11);
        LocalDate secondDate = LocalDate.of(2021, 4, 11);

        Double firstSalary = 124.56;
        Double secondSalary = 2222.22;
        pracownikDtoList.add(AktywnyPracownikDto.builder().id(1L).name("pracownik")
                .salary(firstSalary)
                .dataZatrudnienia(firstDate).build());

        pracownikDtoList.add(AktywnyPracownikDto.builder().id(2L).name("pracownik2")
                .salary(secondSalary)
                .dataZatrudnienia(secondDate).build());
        Mockito.when(aktywnyPracownikService.findAll()).thenReturn(pracownikDtoList);

        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].name", is("pracownik")))
                .andExpect(jsonPath("$.[0].salary", is(firstSalary)))
                .andExpect(jsonPath("$.[0].dataZatrudnienia", is(firstDate.toString())))

                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].name", is("pracownik2")))
                .andExpect(jsonPath("$.[1].salary", is(secondSalary)))
                .andExpect(jsonPath("$.[1].dataZatrudnienia", is(secondDate.toString())))
        ;

    }

    @Test
    void createPracownik() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 5, 11);
        Double salary = 124.56;


        AktywnyPracownikDto aktywnyPracownikDto1 = AktywnyPracownikDto.builder().name("pracownik")
                .salary(salary)
                .dataZatrudnienia(localDate)
                .build();
        AktywnyPracownikDto aktywnyPracownikDtoSaved = AktywnyPracownikDto.builder().id(4L).name("pracownik").build();
        Mockito.when(aktywnyPracownikService.savePracownik(aktywnyPracownikDto1)).thenReturn(aktywnyPracownikDtoSaved);

        String jsonContent = gson.toJson(aktywnyPracownikDto1);

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
        LocalDate localDate = LocalDate.of(2021, 6, 11);
        Double salary = 0.00;

        AktywnyPracownikDto aktywnyPracownikDto1 = AktywnyPracownikDto.builder()
                .salary(salary)
                .dataZatrudnienia(localDate)
                .build();

        String jsonContent = gson.toJson(aktywnyPracownikDto1);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.exceptions.salary[0].code", is("Min")))
                .andExpect(jsonPath("$.exceptions.salary[0].message", is("must be greater than or equal to 1")))

                .andExpect(jsonPath("$.exceptions.name[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.name[0].message", is("must not be empty")))

                .andExpect(jsonPath("$.exceptions.dataZatrudnienia[0].code", is("PastOrPresent")))
                .andExpect(jsonPath("$.exceptions.dataZatrudnienia[0].message", is("must be a date in the past or in the present")))
        ;
    }

    @Test
    void updatePracownik() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 5, 11);
        Double salary = 124.56;

        Long id = 4L;
        AktywnyPracownikDto aktywnyPracownikDto1 = AktywnyPracownikDto.builder().name("pracownikUpdated")
                .salary(salary)
                .dataZatrudnienia(localDate)
                .build();
        AktywnyPracownikDto aktywnyPracownikDtoSaved = AktywnyPracownikDto.builder().id(4L).name("pracownikUpdated").build();

        Mockito.when(aktywnyPracownikService.updatePracownik(id, aktywnyPracownikDto1)).thenReturn(aktywnyPracownikDtoSaved);

        String jsonContent = gson.toJson(aktywnyPracownikDto1);

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
        LocalDate localDate = LocalDate.of(2021, 6, 11);
        Double salary = 0.00;

        AktywnyPracownikDto aktywnyPracownikDto1 = AktywnyPracownikDto.builder()
                .salary(salary)
                .dataZatrudnienia(localDate)
                .build();

        String jsonContent = gson.toJson(aktywnyPracownikDto1);

        mockMvc.perform(put(url + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.exceptions.salary[0].code", is("Min")))
                .andExpect(jsonPath("$.exceptions.salary[0].message", is("must be greater than or equal to 1")))

                .andExpect(jsonPath("$.exceptions.name[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.name[0].message", is("must not be empty")))

                .andExpect(jsonPath("$.exceptions.dataZatrudnienia[0].code", is("PastOrPresent")))
                .andExpect(jsonPath("$.exceptions.dataZatrudnienia[0].message", is("must be a date in the past or in the present")))
        ;
    }

    @Test
    void deletePracownik() throws Exception {
        Long id = 4L;
        Mockito.doNothing().when(aktywnyPracownikService).deleteAktywnyPracownik(id);

        mockMvc.perform(delete(url + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
        ;
    }

}

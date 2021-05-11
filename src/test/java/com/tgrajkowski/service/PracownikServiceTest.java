package com.tgrajkowski.service;

import com.tgrajkowski.model.pracownik.PracownikDto;
import com.tgrajkowski.model.pracownik.PracownikRepository;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownik;
import com.tgrajkowski.model.pracownik.Pracownik;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikDataDto;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PracownikServiceTest {
    @Autowired
    private PracownikService pracownikService;
    @Autowired
    private AktywnyPracownikService aktywnyPracownikService;

    @Autowired
    private PracownikRepository pracownikRepository;

    @Test
    void testSavePracownik() {
        PracownikDto pracownikDto = PracownikDto.builder().name("pracownik").build();
        PracownikDto pracownikDtoSaved = pracownikService.savePracownik(pracownikDto);
        Optional<Pracownik> optionalPracownik = pracownikRepository.findById(pracownikDtoSaved.getId());
        Assert.assertTrue(optionalPracownik.isPresent());
        Assert.assertEquals(pracownikDtoSaved.getId(), optionalPracownik.get().getId());
        Assert.assertEquals(pracownikDtoSaved.getName(), optionalPracownik.get().getName());
    }

    @Test
    void findAll() {
        List<Pracownik> pracownikList= new ArrayList<>();
        pracownikList.add(Pracownik.builder().name("pracownik1").build());
        pracownikList.add(Pracownik.builder().name("pracownik2").build());
        pracownikRepository.saveAll(pracownikList);
        List<PracownikDto> pracownikDtoList= pracownikService.findAll();
        Assert.assertEquals(pracownikDtoList.size(), pracownikDtoList.size());
        Assert.assertEquals(pracownikDtoList.get(0).getId(), pracownikDtoList.get(0).getId());
        Assert.assertEquals(pracownikDtoList.get(0).getName(), pracownikDtoList.get(0).getName());

        Assert.assertEquals(pracownikDtoList.get(1).getId(), pracownikDtoList.get(1).getId());
        Assert.assertEquals(pracownikDtoList.get(1).getName(), pracownikDtoList.get(1).getName());
    }

    @Test
    void updatePracownik() {
        Pracownik pracownik = Pracownik.builder().name("pracownik1").build();
        pracownikRepository.save(pracownik);
        PracownikDto pracownikDto = PracownikDto.builder().name("pracownikUpdated").build();
        PracownikDto pracownikDtoUpdated = pracownikService.updatePracownik(pracownik.getId(), pracownikDto);

        Optional<Pracownik> optionalPracownik = pracownikRepository.findById(pracownikDtoUpdated.getId());
        Assert.assertTrue(optionalPracownik.isPresent());
        Assert.assertEquals(pracownikDtoUpdated.getId(), optionalPracownik.get().getId());
        Assert.assertEquals(pracownikDtoUpdated.getName(), optionalPracownik.get().getName());

    }

    @Test
    void deletePracownik() {
        Pracownik pracownik = Pracownik.builder().name("pracownik1").build();
        pracownikRepository.save(pracownik);

        pracownikService.deletePracownik(pracownik.getId());

        Optional<Pracownik> optionalPracownikDeleted = pracownikRepository.findById(pracownik.getId());
        Assert.assertTrue(optionalPracownikDeleted.isEmpty());
    }

}

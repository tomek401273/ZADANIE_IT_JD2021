package com.tgrajkowski.service;

import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownik;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikDto;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class AktywnyPracownikServiceTest {
    @Autowired
    private AktywnyPracownikService pracownikService;
    @Autowired
    private AktywnyPracownikRepository pracownikRepository;

    @Test
    void testSavePracownik() {
        LocalDate dataZatrudnienia= LocalDate.now();
        AktywnyPracownikDto pracownikDto = AktywnyPracownikDto.builder().name("pracownik")
                .dataZatrudnienia(dataZatrudnienia)
                .salary(20_000.00)
                .build();
        AktywnyPracownikDto pracownikDtoSaved = pracownikService.savePracownik(pracownikDto);
        Optional<AktywnyPracownik> optionalPracownik = pracownikRepository.findById(pracownikDtoSaved.getId());
        Assert.assertTrue(optionalPracownik.isPresent());
        Assert.assertEquals(pracownikDtoSaved.getId(), optionalPracownik.get().getId());
        Assert.assertEquals(pracownikDtoSaved.getName(), optionalPracownik.get().getName());
        Assert.assertEquals(pracownikDtoSaved.getSalary(), optionalPracownik.get().getSalary());
        Assert.assertEquals(pracownikDtoSaved.getDataZatrudnienia(), optionalPracownik.get().getDataZatrudnienia());
    }

    @Test
    void findAll() {
        LocalDate dataZatrudnienia= LocalDate.now();

        List<AktywnyPracownik> pracownikList = new ArrayList<>();
        pracownikList.add(AktywnyPracownik.builder()
                .dataZatrudnienia(dataZatrudnienia)
                .salary(20_000.00)
                .name("pracownik1").build());
        pracownikList.add(AktywnyPracownik.builder().name("pracownik2")
                .dataZatrudnienia(LocalDate.now().plusDays(1))
                        .salary(30_000.00)
                .build());
        pracownikRepository.saveAll(pracownikList);
        List<AktywnyPracownikDto> pracownikDtoList = pracownikService.findAll();
        Assert.assertEquals(pracownikDtoList.size(), pracownikDtoList.size());
        Assert.assertEquals(pracownikDtoList.get(0).getId(), pracownikDtoList.get(0).getId());
        Assert.assertEquals(pracownikDtoList.get(0).getName(), pracownikDtoList.get(0).getName());

        Assert.assertEquals(pracownikDtoList.get(1).getId(), pracownikDtoList.get(1).getId());
        Assert.assertEquals(pracownikDtoList.get(1).getName(), pracownikDtoList.get(1).getName());
        Assert.assertEquals(pracownikDtoList.get(1).getSalary(), pracownikDtoList.get(1).getSalary());
        Assert.assertEquals(pracownikDtoList.get(1).getDataZatrudnienia(), pracownikDtoList.get(1).getDataZatrudnienia());
    }

    @Test
    void updatePracownik() {
        AktywnyPracownik pracownik = AktywnyPracownik.builder().name("pracownik1")
                .dataZatrudnienia(LocalDate.now().plusDays(1))
                .salary(30_000.00).build();
        pracownikRepository.save(pracownik);
        AktywnyPracownikDto pracownikDto = AktywnyPracownikDto.builder().name("pracownikUpdated")
                .dataZatrudnienia(LocalDate.now().plusDays(2))
                .salary(40_000.00)
                .build();
        AktywnyPracownikDto pracownikDtoUpdated = pracownikService.updatePracownik(pracownik.getId(), pracownikDto);

        Optional<AktywnyPracownik> optionalPracownik = pracownikRepository.findById(pracownikDtoUpdated.getId());
        Assert.assertTrue(optionalPracownik.isPresent());
        Assert.assertEquals(pracownikDtoUpdated.getId(), optionalPracownik.get().getId());
        Assert.assertEquals(pracownikDtoUpdated.getName(), optionalPracownik.get().getName());
        Assert.assertEquals(pracownikDtoUpdated.getDataZatrudnienia(), optionalPracownik.get().getDataZatrudnienia());
        Assert.assertEquals(pracownikDtoUpdated.getSalary(), optionalPracownik.get().getSalary());
    }

    @Test
    void deletePracownik() {
        AktywnyPracownik pracownik = AktywnyPracownik.builder().name("pracownik1").build();
        pracownikRepository.save(pracownik);

        pracownikService.deleteAktywnyPracownik(pracownik.getId());

        Optional<AktywnyPracownik> optionalPracownikDeleted = pracownikRepository.findById(pracownik.getId());
        Assert.assertTrue(optionalPracownikDeleted.isEmpty());
    }
}

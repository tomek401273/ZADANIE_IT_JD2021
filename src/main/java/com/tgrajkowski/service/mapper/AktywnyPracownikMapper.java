package com.tgrajkowski.service.mapper;

import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownik;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikDto;
import org.springframework.stereotype.Component;

@Component
public class AktywnyPracownikMapper {
    public AktywnyPracownik mapToAktywnyPracownik(AktywnyPracownikDto aktywnyPracownikDto) {
        return AktywnyPracownik.builder()
                .name(aktywnyPracownikDto.getName())
                .salary(aktywnyPracownikDto.getSalary())
                .dataZatrudnienia(aktywnyPracownikDto.getDataZatrudnienia())
                .build();
    }
    public AktywnyPracownikDto mapToAktywnyPracownikDto(AktywnyPracownik aktywnyPracownik){
        return AktywnyPracownikDto.builder().id(aktywnyPracownik.getId())
                .name(aktywnyPracownik.getName())
                .dataZatrudnienia(aktywnyPracownik.getDataZatrudnienia())
                .salary(aktywnyPracownik.getSalary()).build();
    }
}

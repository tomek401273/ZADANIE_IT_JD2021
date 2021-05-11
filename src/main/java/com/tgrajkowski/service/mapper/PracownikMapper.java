package com.tgrajkowski.service.mapper;

import com.tgrajkowski.model.pracownik.Pracownik;
import com.tgrajkowski.model.pracownik.PracownikDto;
import org.springframework.stereotype.Component;

@Component
public class PracownikMapper {
    public Pracownik mapToPracownik(PracownikDto pracownikDto) {
        return Pracownik.builder().name(pracownikDto.getName()).build();
    }

    public PracownikDto mapToPracownikDto(Pracownik pracownik) {
        return PracownikDto.builder()
                .id(pracownik.getId())
                .name(pracownik.getName())
                .build();
    }
}

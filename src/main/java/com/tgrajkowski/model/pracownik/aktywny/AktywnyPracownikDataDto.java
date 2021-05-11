package com.tgrajkowski.model.pracownik.aktywny;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AktywnyPracownikDataDto {
    private Double salary;
    private LocalDate dataZatrudnienia;
}

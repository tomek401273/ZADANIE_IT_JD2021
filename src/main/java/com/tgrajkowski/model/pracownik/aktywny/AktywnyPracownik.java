package com.tgrajkowski.model.pracownik.aktywny;

import com.tgrajkowski.model.pracownik.Pracownik;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@DiscriminatorValue(value = "AKTYWNY")
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "aktywny_pracownik")
public class AktywnyPracownik extends Pracownik {
    private Double salary;
    private LocalDate dataZatrudnienia;


    public void updatePacownik(AktywnyPracownikDto aktywnyPracownikDto){
        super.updatePacownik(aktywnyPracownikDto);
        salary=aktywnyPracownikDto.getSalary();
        dataZatrudnienia=aktywnyPracownikDto.getDataZatrudnienia();
    }
}

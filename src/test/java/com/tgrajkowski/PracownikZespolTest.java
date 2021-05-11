package com.tgrajkowski;

import com.tgrajkowski.model.pracownik.Pracownik;
import com.tgrajkowski.model.pracownik.PracownikRepository;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownik;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikRepository;
import com.tgrajkowski.model.zespol.Zespol;
import com.tgrajkowski.model.zespol.ZespolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class PracownikZespolTest {
    @Autowired
    private PracownikRepository pracownikRepository;
    @Autowired
    private ZespolRepository zespolRepository;
    @Autowired
    private AktywnyPracownikRepository aktywnyPracownikRepository;

    @Test
    public void savePracownikZespolTest() {
        Pracownik pracownik1 =Pracownik.builder().name("pracownik1").build();
        Pracownik pracownik2 =Pracownik.builder().name("pracownik2").build();
        AktywnyPracownik aktywnyPracownik1= AktywnyPracownik.builder()
                .dataZatrudnienia(LocalDate.now())
                .salary(20_000.00)
                .name("aktywnyPracownik1").build();
        AktywnyPracownik aktywnyPracownik2= AktywnyPracownik.builder()
                .dataZatrudnienia(LocalDate.now())
                .salary(30_000.00)
                .name("aktywnyPracownik1").build();



        Zespol zespol1= Zespol.builder().name("myZespol1")
                .pracownikList(new ArrayList<>()).build();

        zespol1.getPracownikList().add(pracownik1);
        zespol1.getPracownikList().add(aktywnyPracownik1);
        Zespol zespol2= Zespol.builder().pracownikList(new ArrayList<>())
                .name("myZespol2").build();
        zespol2.getPracownikList().add(pracownik1);
        zespol2.getPracownikList().add(pracownik2);
        zespol2.getPracownikList().add(aktywnyPracownik2);

        pracownikRepository.save(pracownik1);
        pracownikRepository.save(pracownik2);
        aktywnyPracownikRepository.save(aktywnyPracownik1);
        aktywnyPracownikRepository.save(aktywnyPracownik2);
        zespolRepository.save(zespol1);
        zespolRepository.save(zespol2);
    }
}

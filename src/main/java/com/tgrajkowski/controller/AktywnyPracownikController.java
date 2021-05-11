package com.tgrajkowski.controller;

import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikDto;
import com.tgrajkowski.service.AktywnyPracownikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pracownik/aktywny/")
public class AktywnyPracownikController {
    private final AktywnyPracownikService aktywnyPracownikService;

    public AktywnyPracownikController(AktywnyPracownikService aktywnyPracownikService) {
        this.aktywnyPracownikService = aktywnyPracownikService;
    }

    @GetMapping
    public List<AktywnyPracownikDto> findAll() {
        return aktywnyPracownikService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createPracownik(@Valid @RequestBody AktywnyPracownikDto aktywnyPracownikDto) {
        AktywnyPracownikDto aktywnyPracownikDtoResult = aktywnyPracownikService.savePracownik(aktywnyPracownikDto);
        return new ResponseEntity<>(aktywnyPracownikDtoResult, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePracownik(@PathVariable("id") Long id, @Valid @RequestBody AktywnyPracownikDto aktywnyPracownikDto) {
        AktywnyPracownikDto aktywnyPracownikDtoResult = aktywnyPracownikService.updatePracownik(id, aktywnyPracownikDto);
        return new ResponseEntity<>(aktywnyPracownikDtoResult, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePracownik(@PathVariable("id") Long id) {
        aktywnyPracownikService.deleteAktywnyPracownik(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

package com.tgrajkowski.controller;

import com.tgrajkowski.model.pracownik.PracownikDto;
import com.tgrajkowski.service.PracownikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pracownik")
public class PracownikController {
    private final PracownikService pracownikService;

    public PracownikController(PracownikService pracownikService) {
        this.pracownikService = pracownikService;
    }

    @GetMapping("/")
    public List<PracownikDto> findAll() {
        return pracownikService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createPracownik(@Valid @RequestBody PracownikDto pracownikDto) {
        PracownikDto aktywnyPracownikDtoResult = pracownikService.savePracownik(pracownikDto);
        return new ResponseEntity<>(aktywnyPracownikDtoResult, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePracownik(@PathVariable("id") Long id, @Valid @RequestBody PracownikDto pracownikDto) {
        PracownikDto pracownikDtoResult = pracownikService.updatePracownik(id, pracownikDto);
        return new ResponseEntity<>(pracownikDtoResult, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePracownik(@PathVariable("id") Long id) {
        pracownikService.deletePracownik(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

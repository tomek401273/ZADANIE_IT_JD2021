package com.tgrajkowski.service;

import com.tgrajkowski.exception.NotFoundResourceException;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownik;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikDto;
import com.tgrajkowski.model.pracownik.aktywny.AktywnyPracownikRepository;
import com.tgrajkowski.service.mapper.AktywnyPracownikMapper;
import com.tgrajkowski.service.mapper.PracownikMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AktywnyPracownikService {

    private final AktywnyPracownikRepository aktywnyPracownikRepository;
    private final AktywnyPracownikMapper aktywnyPracownikMapper;
    private final PracownikMapper pracownikMapper;

    public AktywnyPracownikService(AktywnyPracownikRepository aktywnyPracownikRepository, AktywnyPracownikMapper aktywnyPracownikMapper, PracownikMapper pracownikMapper) {
        this.aktywnyPracownikRepository = aktywnyPracownikRepository;
        this.aktywnyPracownikMapper = aktywnyPracownikMapper;
        this.pracownikMapper = pracownikMapper;
    }

    @Transactional
    public AktywnyPracownikDto savePracownik(AktywnyPracownikDto aktywnyPracownikDto) {
        AktywnyPracownik aktywnyPracownik = aktywnyPracownikMapper.mapToAktywnyPracownik(aktywnyPracownikDto);
        AktywnyPracownik aktywnyPracownikSaved = aktywnyPracownikRepository.save(aktywnyPracownik);
        return aktywnyPracownikMapper.mapToAktywnyPracownikDto(aktywnyPracownikSaved);
    }

    @Transactional
    public AktywnyPracownikDto updatePracownik(Long pracownikId, AktywnyPracownikDto aktywnyPracownikDto) {
        Optional<AktywnyPracownik> aktywnyPracownikOptional = aktywnyPracownikRepository.findById(pracownikId);
        if (aktywnyPracownikOptional.isEmpty())
            throw new NotFoundResourceException("resource dosen't exist");
        else {
            AktywnyPracownik aktywnyPracownik = aktywnyPracownikOptional.get();
            aktywnyPracownik.updatePacownik(aktywnyPracownikDto);
            AktywnyPracownikDto aktywnyPracownikDtoUpdated
                    = aktywnyPracownikMapper.mapToAktywnyPracownikDto(aktywnyPracownik);
            return aktywnyPracownikDtoUpdated;
        }

    }

    @Transactional
    public void deleteAktywnyPracownik(Long id){
        Optional<AktywnyPracownik> pracownikOptional = aktywnyPracownikRepository.findById(id);
        if (pracownikOptional.isEmpty())
            throw new NotFoundResourceException("resource dosen't exist");
        else {
            aktywnyPracownikRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public List<AktywnyPracownikDto> findAll() {
        return aktywnyPracownikRepository.findAll().stream()
                .map(aktywnyPracownikMapper::mapToAktywnyPracownikDto)
                .collect(Collectors.toList());
    }
}

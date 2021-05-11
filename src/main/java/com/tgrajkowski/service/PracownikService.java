package com.tgrajkowski.service;

import com.tgrajkowski.exception.NotFoundResourceException;
import com.tgrajkowski.model.pracownik.Pracownik;
import com.tgrajkowski.model.pracownik.PracownikDto;
import com.tgrajkowski.model.pracownik.PracownikRepository;
import com.tgrajkowski.service.mapper.PracownikMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PracownikService {

    private final PracownikRepository pracownikRepository;
    private final PracownikMapper pracownikMapper;

    @Autowired
    public PracownikService(PracownikRepository pracownikRepository, PracownikMapper pracownikMapper) {
        this.pracownikRepository = pracownikRepository;
        this.pracownikMapper = pracownikMapper;
    }

    @Transactional
    public PracownikDto savePracownik(PracownikDto pracownikDto) {
        Pracownik pracownik = pracownikMapper.mapToPracownik(pracownikDto);
        Pracownik pracownikSaved =pracownikRepository.save(pracownik);
        PracownikDto pracownikDtoResult = pracownikMapper.mapToPracownikDto(pracownikSaved);
        return pracownikDtoResult;
    }

    @Transactional(readOnly = true)
    public List<PracownikDto> findAll() {
        return pracownikRepository.findAll().stream()
                .map(pracownikMapper::mapToPracownikDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PracownikDto updatePracownik(Long id, PracownikDto pracownikDto) {
        Optional<Pracownik> pracownikOptional = pracownikRepository.findById(id);
        if (pracownikOptional.isEmpty())
            throw new NotFoundResourceException("resource dosen't exist");
        else {
            Pracownik pracownik = pracownikOptional.get();
            pracownik.updatePacownik(pracownikDto);
            PracownikDto pracownikDtoUpdated  = pracownikMapper.mapToPracownikDto(pracownik);
            return pracownikDtoUpdated;
        }
    }

    @Transactional
    public void deletePracownik(Long id) {
        Optional<Pracownik> pracownikOptional = pracownikRepository.findById(id);
        if (pracownikOptional.isEmpty())
            throw new NotFoundResourceException("resource dosen't exist");
        else {
            pracownikRepository.deleteById(id);
        }
    }
}

package com.siit.hospital_manager.service;

import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.repository.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }

    public Specialty findById(Integer specialtyId) {
        return specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found"));
    }

    public void save(Specialty specialty) {
        specialtyRepository.save(specialty);
    }
}

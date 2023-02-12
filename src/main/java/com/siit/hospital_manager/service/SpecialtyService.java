package com.siit.hospital_manager.service;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Admin;
import com.siit.hospital_manager.model.Diagnosis;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.repository.DoctorRepository;
import com.siit.hospital_manager.repository.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final DoctorRepository doctorRepository;

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

    @Transactional
    public void deleteSpecialityByID(Integer id) {
        Specialty specialty = specialtyRepository.findById(id).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid speciality id"));
        List<Doctor> doctorList = doctorRepository.findAll();
        for(Doctor doctor : doctorList){
            if(doctor.getSpecialty()==specialty){
                doctorRepository.delete(doctor);
            }
        }
      specialtyRepository.delete(specialty);
    }
}
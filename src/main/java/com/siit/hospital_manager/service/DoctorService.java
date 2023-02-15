package com.siit.hospital_manager.service;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Patient;
import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.model.dto.CreateDoctorDto;
import com.siit.hospital_manager.model.dto.DoctorDto;
import com.siit.hospital_manager.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    public List<DoctorDto> findAll() {
        return doctorRepository
                .findAll()
                .stream()
                .map(Doctor::toDto)
                .toList();
    }

    public void createDoctor(CreateDoctorDto createDoctorDto) {

        doctorRepository.findByName(createDoctorDto.getName()).ifPresent(doctorInDb -> {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Doctor already exists!");
        });
        Doctor doctor =Doctor
                    .builder()
                    .userName(createDoctorDto.getUserName())
                    .name(createDoctorDto.getName())
                    .specialty(createDoctorDto.getSpecialty())
                      .password(passwordEncoder.encode(createDoctorDto.getPassword()))
                    .isActive(true)
                    .roles("ROLE_DOCTOR")
                    .build();
        doctorRepository.save(doctor);
        }




    @Transactional
    public void deleteDoctorById(Integer id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid doctor id"));
        doctorRepository.delete(doctor);
    }

    public List<Doctor> findAllBySpecialty(Specialty specialty) {
        return doctorRepository.findAllBySpecialty(specialty);
    }

    public Doctor findByUsername(String username) {
        return doctorRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
    }

}

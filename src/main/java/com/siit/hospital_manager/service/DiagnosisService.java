package com.siit.hospital_manager.service;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Appointment;
import com.siit.hospital_manager.model.Diagnosis;
import com.siit.hospital_manager.model.Medication;
import com.siit.hospital_manager.repository.AppointmentsRepository;
import com.siit.hospital_manager.repository.DiagnosisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final AppointmentsRepository appointmentsRepository;

    public void createDiagnose(String name){
        diagnosisRepository.findByName(name).ifPresent(diagnosis -> { throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Diagnose Already Exist");
        });
       Diagnosis diagnosis = new Diagnosis();
       diagnosis.setName(name);
      diagnosisRepository.save(diagnosis);
    }


    @Transactional
    public void deleteDiagnose(Integer id){
        Diagnosis diagnosis = diagnosisRepository.findById(id).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Appointment not found"));

        appointmentsRepository.findAll()
                        .forEach(appointment -> {
                            if (appointment.getDiagnoses().contains(diagnosis))
                                appointment.removeDiagnosis(diagnosis);
                        });

        diagnosisRepository.deleteByIdNativeQuery(diagnosis.getId());
    }
    public Diagnosis findById(Integer diagnosisId){
        return diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found"));}

    public List<Diagnosis> findAll(){
        return diagnosisRepository.findAll();
    }
}

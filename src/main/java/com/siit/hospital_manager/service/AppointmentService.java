package com.siit.hospital_manager.service;

import com.siit.hospital_manager.config.MyUserDetails;
import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.*;
import com.siit.hospital_manager.model.dto.AppointmentDto;
import com.siit.hospital_manager.model.dto.CreateAppointmentDto;
import com.siit.hospital_manager.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentsRepository appointmentsRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<AppointmentDto> findAllByPatientId(Integer id) {
        List<Appointment> appointments = appointmentsRepository.findAllByPatientId(id);

        return appointments
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAll() {
        return appointmentsRepository.findAll()
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAllByUserName(String userName) {
        User patient = userRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")
        );

        List<Appointment> appointments = appointmentsRepository.findAllByPatientId(patient.getId());
        return appointments.stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAllByDoctor(String userName){
        User doctor = userRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));
    List<Appointment> appointments = appointmentsRepository.findAllByDoctorId(doctor.getId());
    return appointments.stream()
            .map(Appointment::toDto)
            .toList();

    }
    @Transactional
    public void deleteAppointmentByIdAndPatient(Integer id, String userName) {
        Patient patient = patientRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid patient id"));

        Appointment appointment = appointmentsRepository.findAppointmentByIdAndPatient(id, patient).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Appointment not found"));

        appointment.setDiagnoses(null);
        appointment.setMedications(null);
        appointment.setProcedures(null);

        appointmentsRepository.deleteByIdNativeQuery(appointment.getId());
    }

    public List<AppointmentDto> create(String name) {

        return null;
    }

    public void save(CreateAppointmentDto createAppointmentDto) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Patient patient = patientRepository.findByUserName(myUserDetails.getUsername()).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid patient id"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .date(createAppointmentDto.getDate())
                .doctor(createAppointmentDto.getDoctor())
                .build();

        appointmentsRepository.save(appointment);
    }

    public Appointment findById(Integer appointmentId) {
        return appointmentsRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }

    public void save(Appointment appointment) {
         appointmentsRepository.save(appointment);
    }
}

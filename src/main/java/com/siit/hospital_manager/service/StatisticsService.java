package com.siit.hospital_manager.service;

import com.siit.hospital_manager.model.Appointment;
import com.siit.hospital_manager.model.Diagnosis;
import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.repository.AppointmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final AppointmentsRepository appointmentsRepository;

    public HashMap<Specialty, Integer> getNumberOfAppointmentsPerSpecialty(){
        HashMap<Specialty, Integer> specialityAppointmentNumbers = new HashMap<>();
        List<Appointment> appointmentList = appointmentsRepository.findAll();

        for (Appointment appointment : appointmentList) {
            Specialty specialty = appointment.getSpecialty();
            if (specialityAppointmentNumbers.containsKey(specialty)) {
                specialityAppointmentNumbers.put(specialty, specialityAppointmentNumbers.get(specialty) + 1);
            } else {
                specialityAppointmentNumbers.put(specialty, 1);
            }
        }
        return specialityAppointmentNumbers;
    }


    public HashMap<Diagnosis,Integer> getNumberOfDiagnoses(){
        HashMap<Diagnosis,Integer> diagnosisNumberList = new HashMap<>();
        List<Appointment> appointmentList= appointmentsRepository.findAll();
        for(Appointment appointment:appointmentList) {
            for (Diagnosis diagnosis : appointment.getDiagnoses()) {
                if (diagnosisNumberList.containsKey(diagnosis)) {
                    diagnosisNumberList.put(diagnosis, diagnosisNumberList.get(diagnosis) + 1);
                } else {
                    diagnosisNumberList.put(diagnosis, 1);
                }
            }
        }
        return diagnosisNumberList;
    }

}

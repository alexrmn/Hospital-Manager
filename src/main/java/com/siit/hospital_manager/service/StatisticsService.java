package com.siit.hospital_manager.service;

import com.siit.hospital_manager.model.Appointment;
import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.repository.AppointmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
        System.out.println("-------------------------------");
        System.out.println(specialityAppointmentNumbers);
        return specialityAppointmentNumbers;
    }

}

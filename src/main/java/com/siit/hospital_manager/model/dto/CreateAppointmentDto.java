package com.siit.hospital_manager.model.dto;

import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Patient;
import com.siit.hospital_manager.model.Specialty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateAppointmentDto {
    @FutureOrPresent
    @NotNull
    private LocalDateTime date;

    private Patient patient;

    @NotNull
    private Doctor doctor;

    @NotNull
    private Specialty specialty;
}

package com.siit.hospital_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siit.hospital_manager.model.dto.AppointmentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JsonIgnore
    private Specialty specialty;

    @ManyToMany()
    @JoinTable(name = "appointment_diagnosis",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id"))
    private Set<Diagnosis> diagnoses;

    @ManyToMany
    @JoinTable(
            name = "appointment_procedure",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<Procedure> procedures;

    @ManyToMany
    @JoinTable(
            name = "appointment_medication",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private Set<Medication> medications;

    @Column(length = 2000)
    private String summary;

    public AppointmentDto toDto(){

        return AppointmentDto
                .builder()
                .id(id)
                .date(date)
                .patient(patient)
                .doctor(doctor)
                .build();
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        if (this.diagnoses == null) {
            this.diagnoses = new HashSet<>();
        }
        this.diagnoses.add(diagnosis);
    }

    public void removeDiagnosis(Diagnosis diagnosis){
        this.diagnoses.remove(diagnosis);
        diagnosis.getAppointments().remove(this);
    }

    public void addProcedure(Procedure procedure) {
        if (this.procedures == null) {
            this.procedures = new HashSet<>();
        }
        this.procedures.add(procedure);
    }

    public void addMedication(Medication medication){
        if (this.medications == null) {
            this.medications = new HashSet<>();
        }
        this.medications.add(medication);
    }

    public void removeMedication(Medication medication) {
        this.medications.remove(medication);
        medication.getAppointments().remove(this);
    }

    public void removeProcedure(Procedure procedure) {
        this.procedures.remove(procedure);
        procedure.getAppointments().remove(this);
    }
}

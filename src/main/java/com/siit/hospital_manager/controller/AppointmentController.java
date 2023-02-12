package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.config.MyUserDetails;
import com.siit.hospital_manager.model.*;
import com.siit.hospital_manager.model.dto.AppointmentDto;
import com.siit.hospital_manager.model.dto.CreateAppointmentDto;
import com.siit.hospital_manager.repository.SpecialtyRepository;
import com.siit.hospital_manager.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/appointment")

@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final SpecialtyRepository specialtyRepository;
    private final DiagnosisService diagnosisService;
    private final ProcedureService procedureService;
    private final MedicationService medicationService;

    private final EmailSender emailSender;

    @GetMapping("/findAllByPatient")
    public String findAllByPatient(Model model, Principal principal) {
        List<AppointmentDto> appointments = appointmentService.findAllByUserName(principal.getName());
        model.addAttribute("appointments", appointments);
        return "appointment/viewAll";
    }

    @GetMapping("/appointmentDetails/{apId}")
    public String findAllDetailsByPatient(@PathVariable Integer apId,Model model) {
        model.addAttribute("appointment", appointmentService.findById(apId));
        model.addAttribute("appointmentDiagnoses", appointmentService.findById(apId).getDiagnoses());
        model.addAttribute("appointmentProcedures", appointmentService.findById(apId).getProcedures());
        model.addAttribute("appointmentMedications", appointmentService.findById(apId).getMedications());
        return "/appointment/appointmentDetails/appointmentsDetails";
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAppointmentById(Model model, @PathVariable Integer id, Principal principal){
         appointmentService.deleteAppointmentByIdAndPatient(id, principal.getName());
    }

    @GetMapping("/create/{specialtyId}")
    public String showCreateSpecialtyForm(@PathVariable Integer specialtyId, Model model) {
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found"));
        model.addAttribute("doctors", doctorService.findAllBySpecialty(specialty));
        model.addAttribute("appointment", CreateAppointmentDto.builder().build());
        return "/appointment/createAppointment";
    }

    @PostMapping("/submitCreateAppointmentForm")
    public String submitCreateAppointmentForm(CreateAppointmentDto createAppointmentDto, BindingResult bindingResult, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        String username = myUserDetails.getUsername();
        Patient patient = patientService.findByUsername(username);
        String userEmail = patient != null ? patient.getEmail() : null;
        emailSender.sendAppointmentConfirmationEmail(userEmail, "Appointment Confirmation",
                "Your appointment has been confirmed we wait you at: " + createAppointmentDto.getDate());
        appointmentService.save(createAppointmentDto);
        return "/appointment/appointmentCreatedSuccessfully";
    }
    @GetMapping("/findAllByDoctor")
    public String findAllByDoctor(Model model, Principal principal){
        List<AppointmentDto> appointments = appointmentService.findAllByDoctor(principal.getName());
        model.addAttribute("doctorAppointments", appointments);
        return "/appointment/viewAppointmentsByDoctor";
    }

    @GetMapping("/{appointmentId}")
    public String showAppointment(@PathVariable Integer appointmentId, Model model) {
        model.addAttribute("appointment", appointmentService.findById(appointmentId));
        model.addAttribute("appointmentDiagnoses", appointmentService.findById(appointmentId).getDiagnoses());
        model.addAttribute("availableDiagnoses", diagnosisService.findAll());
        model.addAttribute("availableProcedures", procedureService.findAll());
        model.addAttribute("appointmentProcedures", appointmentService.findById(appointmentId).getProcedures());
        model.addAttribute("availableMedications", medicationService.findAll());
        model.addAttribute("appointmentMedications", appointmentService.findById(appointmentId).getMedications());

        return  "appointment/viewAppointment";
    }

    @PostMapping("/{appointmentId}/diagnoses")
    public String addDiagnosisToAppointment(@ModelAttribute Diagnosis diagnosis,  @PathVariable Integer appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.addDiagnosis(diagnosis);
        appointmentService.save(appointment);
        return "redirect:/appointment/" + appointmentId;
    }

    @PostMapping("/{appointmentId}/diagnoses/{diagnosisId}/delete")
    public String deleteDiagnosisFromAppointment(@PathVariable Integer appointmentId, @PathVariable Integer diagnosisId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.getDiagnoses().remove(diagnosisService.findById(diagnosisId));
        appointmentService.save(appointment);

        return "redirect:/appointment/" + appointmentId;
    }

    @PostMapping("/{appointmentId}/procedures")
    public String addProcedureToAppointment(@ModelAttribute Procedure procedure, @PathVariable Integer appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.addProcedure(procedure);
        appointmentService.save(appointment);
        return "redirect:/appointment/" + appointmentId;
    }

    @PostMapping("/{appointmentId}/procedures/{procedureId}/delete")
    public String deleteProcedureFromAppointment(@PathVariable Integer appointmentId, @PathVariable Integer procedureId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.getProcedures().remove(procedureService.findById(procedureId));
        appointmentService.save(appointment);
        return "redirect:/appointment/" + appointmentId;
    }

    @PostMapping("/{appointmentId}/medications")
    public String addMedicationToAppointment(@ModelAttribute Medication medication, @PathVariable Integer appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.addMedication(medication);
        appointmentService.save(appointment);
        return "redirect:/appointment/" + appointmentId;
    }

    @PostMapping("/{appointmentId}/medications/{medicationId}/delete")
    public String deleteMedicationFromAppointment(@PathVariable Integer appointmentId, @PathVariable Integer medicationId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.getMedications().remove(medicationService.findById(medicationId));
        appointmentService.save(appointment);
        return "redirect:/appointment/" + appointmentId;
    }

    @PostMapping("/{appointmentId}/save")
    public String saveSummary(@PathVariable Integer appointmentId, @ModelAttribute Appointment editedAppointment) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.setSummary(editedAppointment.getSummary());
        String username=appointment.getPatient().getUserName();
        Patient patient = patientService.findByUsername(username);
        String userEmail = patient != null ? patient.getEmail() : null;
        emailSender.sendAppointmentConfirmationEmail(userEmail, "Appointment Details",
                "Your appointment details have been updated you can watch now the details.");
        appointmentService.save(appointment);
        return "redirect:/appointment/findAllByDoctor";
    }

}

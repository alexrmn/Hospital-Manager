package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.model.Diagnosis;
import com.siit.hospital_manager.repository.DiagnosisRepository;
import com.siit.hospital_manager.repository.PatientRepository;
import com.siit.hospital_manager.service.DiagnosisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/diagnose")
@RequiredArgsConstructor
public class DiagnosisController {

   private final DiagnosisRepository diagnosisRepository;
   private final DiagnosisService diagnosisService;
    private final PatientRepository patientRepository;

    @GetMapping("/viewAllDiagnoses")
    public String viewDiagnoses(Model model){
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        model.addAttribute("diagnosis",diagnosisList);
        return "diagnose/viewAllDiagnoses";
    }

    @GetMapping("/createDiagnose")
    public String createDiagnoseView(Model model){
        model.addAttribute("diagnose", new Diagnosis());
        return "diagnose/createDiagnose";
    }

     @PostMapping("/submitCreateDiagnose")
    public String createDiagnose(@Valid String name) {
        try {
             diagnosisService.createDiagnose(name);
             return "redirect:/diagnose/viewAllDiagnoses";
         } catch (ResponseStatusException e)
         {
             return "entityExistsError";
         }
     }

      @DeleteMapping("/{id}")
      @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDiagnose(@PathVariable Integer id){
      diagnosisService.deleteDiagnose(id);
      }

    @GetMapping("/viewPatientDiagnosis")
    public String viewPatientDiagnosis(Model model, @PathVariable Integer id){
        model.addAttribute("patientDiagnosis", diagnosisService.findById(id));
        return "diagnose/viewPatientDiagnosis";
    }

}

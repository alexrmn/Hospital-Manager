package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.model.Medication;
import com.siit.hospital_manager.repository.MedicationRepository;
import com.siit.hospital_manager.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/medication")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationRepository medicationRepository;
    private final MedicationService medicationService;


    @GetMapping("/viewAllMedication")
    public String viewAllMedication(Model model){
        model.addAttribute("medications",medicationRepository.findAll());
        return "medication/viewAllMedication";
    }


    @GetMapping("/createMedication")
    public String createMedicationView(Model model){
        model.addAttribute("medication",new Medication());
        return"medication/createMedication";
    }


    @PostMapping("/submitCreateMedication")
    public String createMedication(String name){
        try{
            medicationService.createMedication(name);
            return"redirect:/medication/viewAllMedication";
        }catch (ResponseStatusException e){
            return "/entityExistsError";
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteMedication(@PathVariable Integer id){
        medicationService.deleteMedication(id);
    }

    @GetMapping("/viewPatientMedication")
    public String viewPatientMedication(Model model, Integer id){
        model.addAttribute("patientMedication", medicationService.findById(id));
        return "/medication/viewPatientMedication";
    }
}

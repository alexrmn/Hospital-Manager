package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.model.Procedure;
import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/specialty")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping("/{specialtyId}")
    public String showSpecialtyPage(@PathVariable Integer specialtyId, Model model) {
        model.addAttribute("specialty", specialtyService.findById(specialtyId));
        return "specialty/showSpecialty";
    }

    @GetMapping("/createSpecialty")
    public String createProcedure(Model model){
        model.addAttribute("specialty",new Specialty());
        return "specialty/createSpecialty";
    }

    @PostMapping("/submitCreateSpecialty")
    public String createProcedure(@ModelAttribute Specialty specialty){
        specialtyService.save(specialty);
        return "redirect:/specialty/viewAllSpecialties";
    }

    @GetMapping("/viewAllSpecialties")
    public String viewAllProcedure(Model model){
        model.addAttribute("specialties",specialtyService.findAll());
        return "specialty/viewAllSpecialties";
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDoctorById(Model model, @PathVariable Integer id){
        specialtyService.deleteSpecialityByID(id);
    }



}
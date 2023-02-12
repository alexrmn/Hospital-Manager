package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.model.Procedure;
import com.siit.hospital_manager.repository.ProcedureRepository;
import com.siit.hospital_manager.service.ProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/procedure")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureService procedureService;
    private final ProcedureRepository procedureRepository;

    @GetMapping("/createProcedure")
    public String createProcedure(Model model){
        model.addAttribute("procedure",new Procedure());
        return "procedure/createProcedure";
    }

    @GetMapping("/viewAllProcedure")
    public String viewAllProcedure(Model model){
        model.addAttribute("procedures",procedureRepository.findAll());
        return "procedure/viewAllProcedure";
    }

    @PostMapping("/submitCreateProcedure")
    public String createProcedure(String name){
        try{
            procedureService.createProcedure(name);
            return "redirect:/procedure/viewAllProcedure";
        }catch (ResponseStatusException e){
            return "entityExistsError";
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProcedure(@PathVariable Integer id){
        procedureService.deleteProcedure(id);
    }

}

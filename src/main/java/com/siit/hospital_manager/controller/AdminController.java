package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Admin;
import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.model.dto.CreateAdminDto;
import com.siit.hospital_manager.repository.AdminRepository;
import com.siit.hospital_manager.service.AdminService;
import com.siit.hospital_manager.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminRepository adminRepository;
    private final StatisticsService statisticsService;


    @GetMapping("/viewAllAdmins")
    public String viewAllAdmins(Model model){
        model.addAttribute("admins",adminRepository.findAll());
        return "admin/viewAllAdmins";
    }



    @GetMapping("/createAdmin")
    public String createAdmin(Model model){
        model.addAttribute("admin", CreateAdminDto.builder().build());
        return "admin/createAdmin";
    }

    @PostMapping("/submitCreateAdmin")
    public String submitAdmin(CreateAdminDto createAdminDto){
       try {
           adminService.createAdmin(createAdminDto);
       }catch (ResponseStatusException e){
           return "/entityExistsError";
       }
        return "index";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDoctorById(Model model, @PathVariable Integer id){
       adminService.deleteAdminByID(id);
    }

    @GetMapping("/show-statistics")
    public String showStatistics(Model model) {
        Map<Specialty, Integer> statistics = statisticsService.getNumberOfAppointmentsPerSpecialty();

        List<Specialty> specialties = new ArrayList<>(statistics.keySet());
        List<Integer> appointments = new ArrayList<>(statistics.values());

        model.addAttribute("specialties", specialties);
        model.addAttribute("appointments", appointments);

        return "admin/showStatistics";
    }

}

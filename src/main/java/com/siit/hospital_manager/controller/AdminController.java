package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.model.Diagnosis;
import com.siit.hospital_manager.model.Specialty;
import com.siit.hospital_manager.model.dto.CreateAdminDto;
import com.siit.hospital_manager.repository.AdminRepository;
import com.siit.hospital_manager.service.AdminService;
import com.siit.hospital_manager.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
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

    @GetMapping("/showStatisticsSpecialty")
    public String showStatisticsSpecialty(Model model) {
        Map<Specialty, Integer> statisticsMap = statisticsService.getNumberOfAppointmentsPerSpecialty();
        Map<String, Integer> statistics = new HashMap<>();
        statisticsMap.forEach(((specialty, integer) -> statistics.put(specialty.getName(), integer)));
        model.addAttribute("statistics",statistics);
        return "admin/showStatisticsSpecialty";
    }

    @GetMapping("/showStatisticsDiagnosis")
    public String showStatisticsDiagnosis(Model model) {
        Map<Diagnosis,Integer> diagnosisStatistcMap = statisticsService.getNumberOfDiagnoses();
        Map<String,Integer> diagnosticStatistics = new HashMap<>();
        diagnosisStatistcMap.forEach(((diagnosis, integer) -> diagnosticStatistics.put(diagnosis.getName(),integer)));
        model.addAttribute("diagnosis" , diagnosticStatistics);
        return "admin/showStatisticsDiagnosis";
    }

}

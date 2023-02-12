package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Admin;
import com.siit.hospital_manager.model.dto.CreateAdminDto;
import com.siit.hospital_manager.repository.AdminRepository;
import com.siit.hospital_manager.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminRepository adminRepository;


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


}

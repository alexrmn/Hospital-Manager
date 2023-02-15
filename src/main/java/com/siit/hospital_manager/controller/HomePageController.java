package com.siit.hospital_manager.controller;


import com.siit.hospital_manager.config.MyUserDetails;
import com.siit.hospital_manager.service.SpecialtyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final SpecialtyService specialtyService;

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("message", "Hospital Manager v1");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "accountCreated", required = false) boolean accountCreated){
        if (accountCreated) {
            model.addAttribute("successMessage", "Account created successfully!");
        }
        return "login";
    }

    @GetMapping("/default")
    public String successPage(HttpServletRequest request){
        if(request.isUserInRole("ADMIN")){
            return "redirect:/";
        } else if (request.isUserInRole("DOCTOR")) {
            return "redirect:/indexDoctor";
        } else
            return "redirect:/indexUser";

    }
    @GetMapping("/indexDoctor")
    public String indexDoctor(Model model){
        return "indexDoctor";
    }

    @GetMapping("/indexUser")
    public String indexUser(Model model){
        model.addAttribute("specialties", specialtyService.findAll());
        return "indexUser2";
    }

//    @GetMapping("/dashboard")
//    public String dashBoard(Model model, Authentication authentication){
//        model.addAttribute("userName", authentication.getName());
//        model.addAttribute("isAdmin", isAdmin(authentication));
//        model.addAttribute("isPatient", isPatient(authentication));
//        model.addAttribute("isDoctor", isDoctor(authentication));
//
//
//        model.addAttribute("message", "Hospital Manager v1");
//        return "dashboard/dashboard";
//    }

}

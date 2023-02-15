package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.config.MyUserDetails;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.LiveChat;
import com.siit.hospital_manager.model.Patient;
import com.siit.hospital_manager.model.User;
import com.siit.hospital_manager.repository.DoctorRepository;
import com.siit.hospital_manager.repository.UserRepository;
import com.siit.hospital_manager.service.AppointmentService;
import com.siit.hospital_manager.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class LiveChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;


    @GetMapping("/chat")
    public String chat(Authentication authentication, Model model) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        String username = myUserDetails.getUsername();
        model.addAttribute("user",username);
        return "chat";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public LiveChat messageSender(@Payload LiveChat chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public LiveChat addUser(@Payload LiveChat chatMessage, SimpMessageHeaderAccessor headerAccessor, Authentication authentication) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        String username = myUserDetails.getUsername();
        headerAccessor.getSessionAttributes().put("username", username);
        chatMessage.setSender(username);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
        notifyDoctors(doctorRepository.findAll());
        return chatMessage;
    }

    private void notifyDoctors(List<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            String doctorName = doctor.getName();
            messagingTemplate.convertAndSendToUser(doctorName, "/queue/notify", "A new user has connected to the chat");
        }
    }


}

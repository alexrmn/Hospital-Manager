package com.siit.hospital_manager.service;

import com.siit.hospital_manager.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DoctorRepository doctorRepository;

}

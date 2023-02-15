package com.siit.hospital_manager.repository;

import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByName(String name);
    Optional<Doctor> findByUserName(String name);

    List<Doctor> findAllBySpecialty(Specialty specialty);

}

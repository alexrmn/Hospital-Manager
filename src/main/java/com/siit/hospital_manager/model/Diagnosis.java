package com.siit.hospital_manager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "diagnoses")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "diagnoses")
    private List<Appointment> appointments;


    @Override
    public String toString() {
        return "Diagnosis{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

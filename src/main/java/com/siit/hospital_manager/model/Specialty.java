package com.siit.hospital_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "specialties")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "specialty")
    @JsonIgnore
    private List<Doctor> doctors;

    @Column(length = 1000)
    @NotBlank
    private String description;

    @Column(length = 1000)
    @NotBlank
    private String imageURL;


    @Override
    public String toString() {
        return name;
    }
}

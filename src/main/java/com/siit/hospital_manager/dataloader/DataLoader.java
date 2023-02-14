package com.siit.hospital_manager.dataloader;

import com.siit.hospital_manager.model.*;
import com.siit.hospital_manager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


//@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final BCryptPasswordEncoder encoder;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ProcedureRepository procedureRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final MedicationRepository medicationRepository;
    private final AppointmentsRepository appointmentsRepository;
    private final AdminRepository adminRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //creating specialties
        Specialty cardiology = Specialty.builder()
                .id(1)
                .name("Cardiology")
                .description("Cardiology is a medical specialty that deals with the diagnosis, " +
                        "treatment and prevention of diseases related to the heart and blood vessels (cardiovascular system). " +
                        "Cardiologists are trained in diagnosing and managing conditions such as heart attacks, heart failure, " +
                        "arrhythmias, congenital heart defects, and hypertension, among others. The field of cardiology also " +
                        "includes non-invasive and invasive procedures for diagnosing and treating heart conditions, " +
                        "such as stress tests, electrocardiograms, and angioplasties.")
                .imageURL("https://i.ibb.co/k9F69JR/CARDIOLOGY.png")
                .build();
        Specialty rheumatology = Specialty.builder()
                .id(2)
                .name("Rheumatology")
                .description("Rheumatology is a medical specialty that focuses on the diagnosis and treatment of rheumatic diseases," +
                        " which are conditions that affect the joints, bones, and muscles. " +
                        "These diseases often cause inflammation, pain, and stiffness, and can also affect internal organs and the immune system. " +
                        "Rheumatologists use a combination of physical exams, lab tests, imaging studies, " +
                        "and patient history to diagnose and manage these conditions, which can include arthritis, lupus, " +
                        "fibromyalgia, and gout, among others.")
                .imageURL("https://i.ibb.co/h7NH3Lp/CARDIOLOGY-1.png")
                .build();
        Specialty generalSurgery = Specialty.builder()
                .id(3)
                .name("General Surgery")
                .description("General surgery is a surgical specialty that focuses on alimentary canal and abdominal contents including" +
                        " the esophagus, stomach, small intestine, large intestine, liver, pancreas, gallbladder, appendix and bile ducts, " +
                        "and often the thyroid gland. They also deal with diseases involving the skin, breast, soft tissue, trauma, " +
                        "peripheral artery disease and hernias and perform endoscopic as such as gastroscopy, colonoscopy and " +
                        "laparoscopic procedures.")
                .imageURL("https://i.ibb.co/jZGz5bh/general-surgery.png")
                .build();
        Specialty neurology = Specialty.builder()
                .id(4)
                .name("Neurology")
                .description("Neurology is the branch of medicine dealing with the diagnosis and treatment of all categories of conditions and disease involving the" +
                        " brain, the spinal cord and the peripheral nerves. " +
                        "Neurological practice relies heavily on the field of neuroscience, the scientific study of the nervous system. " +
                        "A neurologist is a physician specializing in neurology and trained to investigate, diagnose and treat neurological disorders.")
                .imageURL("https://i.ibb.co/wc5BxM2/neurology.png")
                .build();

        specialtyRepository.save(cardiology);
        specialtyRepository.save(rheumatology);
        specialtyRepository.save(generalSurgery);
        specialtyRepository.save(neurology);

        //creating doctors
        Doctor doctor1 = Doctor.builder()
                .userName("doctor1")
                .password(encoder.encode("doctor1"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("Gregory House")
                .specialty(cardiology)
                .build();

        Doctor doctor2 = Doctor.builder()
                .userName("doctor2")
                .password(encoder.encode("doctor2"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("James Wilson")
                .specialty(rheumatology)
                .build();

        Doctor doctor3 = Doctor.builder()
                .userName("doctor3")
                .password(encoder.encode("doctor3"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("James Smith")
                .specialty(generalSurgery)
                .build();

        Doctor doctor4 = Doctor.builder()
                .userName("doctor4")
                .password(encoder.encode("doctor4"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("Michael Andrew")
                .specialty(neurology)
                .build();

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);
        doctorRepository.save(doctor4);


        //creating patients
        Patient patient1 = Patient.builder()
                .userName("patient1")
                .password(encoder.encode("patient1"))
                .isActive(true)
                .roles("ROLE_PATIENT")
                .name("Will Smith")
                .age(33)
                .phoneNumber("0752111222")
                .email("patient1@mail.com")
                .build();

        Patient patient2 = Patient.builder()
                .userName("patient2")
                .password(encoder.encode("patient2"))
                .isActive(true)
                .roles("ROLE_PATIENT")
                .name("Harry Potter")
                .age(21)
                .phoneNumber("0752111222")
                .email("patient2@mail.com")
                .build();

        patientRepository.save(patient1);
        patientRepository.save(patient2);

        //creating admin
        Admin admin = Admin.builder()
                .userName("admin")
                .password(encoder.encode("admin"))
                .isActive(true)
                .roles("ROLE_ADMIN")
                .build();
        adminRepository.save(admin);


        //creating procedures
        Procedure eco = Procedure.builder()
                .name("Echocardiography")
                .build();

        Procedure xRay = Procedure.builder()
                .name("X-Ray")
                .build();

        procedureRepository.save(eco);
        procedureRepository.save(xRay);

        //creating Diagnoses
        Diagnosis diagnosis1 = Diagnosis.builder()
                .name("Hypertension")
                .build();

        Diagnosis diagnosis2 = Diagnosis.builder()
                .name("Lupus")
                .build();

        diagnosisRepository.save(diagnosis1);
        diagnosisRepository.save(diagnosis2);

        //creating Medications
        Medication enalapril = Medication.builder()
                .name("Enalapril")
                .build();

        medicationRepository.save(enalapril);


        //creating appointments
        Set<Diagnosis> diagnosisSet = new HashSet<>();
        diagnosisSet.add(diagnosis1);
        diagnosisSet.add(diagnosis2);

        Appointment appointment = Appointment.builder()
                .date(LocalDateTime.now())
                .patient(patient1)
                .doctor(doctor1)
                .diagnoses(diagnosisSet)
                .build();


    }
}
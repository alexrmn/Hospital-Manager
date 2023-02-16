package com.siit.hospital_manager.dataloader;

import com.siit.hospital_manager.model.*;
import com.siit.hospital_manager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
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
//        Specialty neurology = Specialty.builder()
//                .id(4)
//                .name("Neurology")
//                .description("Neurology is the branch of medicine dealing with the diagnosis and treatment of all categories of conditions and disease involving the" +
//                        " brain, the spinal cord and the peripheral nerves. " +
//                        "Neurological practice relies heavily on the field of neuroscience, the scientific study of the nervous system. " +
//                        "A neurologist is a physician specializing in neurology and trained to investigate, diagnose and treat neurological disorders.")
//                .imageURL("https://i.ibb.co/wc5BxM2/neurology.png")
//                .build();

        specialtyRepository.save(cardiology);
        specialtyRepository.save(rheumatology);
        specialtyRepository.save(generalSurgery);
//        specialtyRepository.save(neurology);

        //creating doctors
        Doctor doctor1 = Doctor.builder()
                .id(1)
                .userName("doctor1")
                .password(encoder.encode("doctor1"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("Gregory House")
                .specialty(cardiology)
                .build();

        Doctor doctor2 = Doctor.builder()
                .id(2)
                .userName("doctor2")
                .password(encoder.encode("doctor2"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("James Wilson")
                .specialty(rheumatology)
                .build();

        Doctor doctor3 = Doctor.builder()
                .id(3)
                .userName("doctor3")
                .password(encoder.encode("doctor3"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("James Smith")
                .specialty(generalSurgery)
                .build();

        Doctor doctor4 = Doctor.builder()
                .id(4)
                .userName("doctor4")
                .password(encoder.encode("doctor4"))
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .name("Michael Andrew")
                .specialty(cardiology)
                .build();

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);
        doctorRepository.save(doctor4);


        //creating patients
        Patient patient1 = Patient.builder()
                .id(5)
                .userName("patient1")
                .password(encoder.encode("patient1"))
                .isActive(true)
                .roles("ROLE_PATIENT")
                .name("Will Smith")
                .age(33)
                .phoneNumber("0752111222")
                .email("roman.alex.93@gmail.com")
                .build();

        Patient patient2 = Patient.builder()
                .id(6)
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
                .id(7)
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

        Procedure bloodTests = Procedure.builder()
                .name("Blood Tests")
                .build();

        Procedure ecg = Procedure.builder()
                .name("ECG")
                .build();

        procedureRepository.save(eco);
        procedureRepository.save(xRay);
        procedureRepository.save(bloodTests);
        procedureRepository.save(ecg);

        //creating Diagnoses
        Diagnosis hypertension = Diagnosis.builder()
                .id(1)
                .name("Hypertension")
                .build();

        Diagnosis lupus = Diagnosis.builder()
                .id(2)
                .name("Lupus")
                .build();

        Diagnosis atrialFibrillation = Diagnosis.builder()
                .id(3)
                .name("Atrial Fibrillation")
                .build();

        Diagnosis appendicitis = Diagnosis.builder()
                .id(4)
                .name("Appendicitis")
                .build();

        Diagnosis atrialFlutter = Diagnosis.builder()
                .id(5)
                .name("Atrial Flutter")
                .build();

        Diagnosis cholecystitis = Diagnosis.builder()
                .id(6)
                .name("Cholecystitis")
                .build();


        diagnosisRepository.save(hypertension);
        diagnosisRepository.save(lupus);
        diagnosisRepository.save(atrialFibrillation);
        diagnosisRepository.save(appendicitis);
        diagnosisRepository.save(atrialFlutter);
        diagnosisRepository.save(cholecystitis);

        //creating Medications
        Medication enalapril = Medication.builder()
                .name("Enalapril")
                .build();

        Medication adrenaline = Medication.builder()
                .name("Adrenaline")
                .build();

        Medication lidocaine = Medication.builder()
                .name("Lidocaine")
                .build();

        Medication amiodarone = Medication.builder()
                .name("Amiodarone")
                .build();

        Medication ibuprofen = Medication.builder()
                .name("Ibuprofen")
                .build();

        medicationRepository.save(enalapril);
        medicationRepository.save(adrenaline);
        medicationRepository.save(lidocaine);
        medicationRepository.save(amiodarone);
        medicationRepository.save(ibuprofen);

        //creating appointments

        Appointment appointment1 = Appointment.builder()
                .date(LocalDateTime.now())
                .patient(patient1)
                .doctor(doctor1)
                .specialty(cardiology)
                .summary("33 year old male presenting with chest pain and shortness of breath. " +
                        "Physical exam finds: Arterial hypertension, irregular heart beat. " +
                        "Ecg shows atrial fibrillation. " +
                        "Echocardiography shows reduced ejection fraction. " +
                        "Recommendations: Eliquis 5mg 1-0-0, Checkup in 1 month")
                .build();
        appointment1.addDiagnosis(hypertension);
        hypertension.addAppointment(appointment1);
        appointmentsRepository.save(appointment1);
        diagnosisRepository.save(hypertension);

        Appointment appointment2 = Appointment.builder()
                .date(LocalDateTime.of(2020, Month.APRIL, 16, 7, 30))
                .patient(patient1)
                .doctor(doctor1)
                .specialty(cardiology)
                .summary("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                        "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                        " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate " +
                        "velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt " +
                        "in culpa qui officia deserunt mollit anim id est laborum.")
                .build();

        appointment2.addDiagnosis(atrialFibrillation);
        appointment2.addDiagnosis(hypertension);
        atrialFibrillation.addAppointment(appointment2);
        hypertension.addAppointment(appointment2);

        Appointment appointment3 = Appointment.builder()
                .date(LocalDateTime.of(2020, Month.APRIL, 16, 10, 30))
                .patient(patient2)
                .doctor(doctor2)
                .specialty(doctor2.getSpecialty())
                .summary("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                        "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                        " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate " +
                        "velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt " +
                        "in culpa qui officia deserunt mollit anim id est laborum.")
                .build();

        appointment3.addDiagnosis(lupus);
        lupus.addAppointment(appointment3);
        appointmentsRepository.save(appointment3);
        diagnosisRepository.save(lupus);

        Appointment appointment4 = Appointment.builder()
                .date(LocalDateTime.of(2022, Month.APRIL, 16, 12, 30))
                .patient(patient1)
                .doctor(doctor1)
                .specialty(cardiology)
                .summary("33 year old male presenting with chest pain and shortness of breath. " +
                        "Physical exam finds: Arterial hypertension, irregular heart beat. " +
                        "Ecg shows atrial fibrillation. " +
                        "Echocardiography shows reduced ejection fraction. " +
                        "Recommendations: Eliquis 5mg 1-0-0, Checkup in 1 month")
                .build();
        appointment4.addDiagnosis(hypertension);
        hypertension.addAppointment(appointment4);
        appointmentsRepository.save(appointment4);
        diagnosisRepository.save(hypertension);

        Appointment appointment5 = Appointment.builder()
                .date(LocalDateTime.of(2021, Month.JULY, 24, 10, 30))
                .patient(patient2)
                .doctor(doctor2)
                .specialty(doctor2.getSpecialty())
                .summary("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                        "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                        " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate " +
                        "velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt " +
                        "in culpa qui officia deserunt mollit anim id est laborum.")
                .build();

        appointment5.addDiagnosis(lupus);
        lupus.addAppointment(appointment5);
        appointmentsRepository.save(appointment5);
        diagnosisRepository.save(lupus);



    }
}
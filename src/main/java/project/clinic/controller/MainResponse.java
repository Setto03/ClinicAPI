package project.clinic.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.clinic.model.Clinic;
import project.clinic.model.Doctor;
import project.clinic.model.Specialization;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainResponse {

    private List<Doctor> doctors;

    private List<Clinic> clinics;

    private List<Specialization> specializations;

    private String message;
}

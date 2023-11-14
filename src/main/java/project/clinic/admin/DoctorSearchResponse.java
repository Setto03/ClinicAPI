package project.clinic.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.clinic.model.Doctor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSearchResponse {

    private List<Doctor> doctors;

    private String message;

}

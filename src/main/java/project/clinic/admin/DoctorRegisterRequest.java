package project.clinic.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegisterRequest {

    private String email;

    private String password;

    private String name;

    private String phone;

    private String address;

    private String gender;

    private String description;

    private String specialization;

}

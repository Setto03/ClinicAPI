package project.clinic.controller.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPatientProfile {

    private String name;

    private String gender;

    private String phone;

    private String birthday;

    private String address;

}

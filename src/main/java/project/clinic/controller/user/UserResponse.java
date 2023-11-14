package project.clinic.controller.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.clinic.model.Session;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String fullName;

    private String gender;

    private String email;

    private String phone;

    private String address;

    private List<Session> sessions;

    private String message;
}

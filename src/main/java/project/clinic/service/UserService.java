package project.clinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.clinic.auth.AuthenticationResponse;
import project.clinic.controller.session.SessionRequest;
import project.clinic.controller.user.ResetPassword;
import project.clinic.controller.user.ResetPasswordRequest;
import project.clinic.controller.user.UserPatientProfile;
import project.clinic.controller.user.UserResponse;
import project.clinic.model.Doctor;
import project.clinic.model.Patient;
import project.clinic.model.Session;
import project.clinic.model.User;
import project.clinic.repository.DoctorRepository;
import project.clinic.repository.SessionRepository;
import project.clinic.repository.UserRepository;
import project.clinic.security.JwtService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final SessionRepository sessionRepository;

    private final DoctorRepository doctorRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse checkEmail(ResetPasswordRequest request) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username.equals(request.getEmail())) {
            var user = repository.findUserByEmail(username).orElseThrow();
            user.setToken("");

            var jwtToken = jwtService.generateToken(user);
            user.setToken(jwtToken);
            repository.save(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("Link reset đã được gửi về email.")
                    .build();
        }

        return AuthenticationResponse.builder()
                .message("Không tìm thấy email")
                .build();
    }

    public AuthenticationResponse resetPassword(ResetPassword request) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = repository.findUserByEmail(username).orElseThrow();

        if (!user.getToken().equals(request.getToken())) {
            return AuthenticationResponse.builder()
                    .message("Invalid token.")
                    .build();
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUpdateAt(String.valueOf(new Date(System.currentTimeMillis())));
        user.setToken("");
        repository.save(user);

        return AuthenticationResponse.builder()
                .message("Cập nhật mật khẩu thành công")
                .build();
    }

    public UserResponse showInfo() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = repository.findUserByEmail(username).orElseThrow();
        List<Session> sessions = sessionRepository.findSessionsByPatientName(user.getFullName());

        return UserResponse.builder()
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .sessions(sessions)
                .build();
    }

    public UserResponse updateSession(Long id, SessionRequest request) {

        Session session = sessionRepository.findById(id).orElseThrow();

        if (session.getStatus().equals("Cancelled")) {
            return respondWithMessage(showInfo(),
                    "Lịch khám bệnh đã bị hủy, không thể thay đổi.");
        }

        Doctor doctor = doctorRepository.findDoctorById(request.getDoctor());

        session.setPrice(request.getPrice());
        session.setTime(request.getTime());
        session.setDoctor(doctor);
        sessionRepository.save(session);

        return respondWithMessage(showInfo(),
                "Cập nhật thông tin lịch khám bệnh thành công.");
    }

    public UserResponse updateProfile(UserPatientProfile profile) {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findUserByEmail(email).orElseThrow();

        var patient = Patient.builder()
                .name(profile.getName())
                .birthday(profile.getBirthday())
                .phone(profile.getPhone())
                .gender(profile.getGender())
                .address(profile.getAddress())
                .build();

        user.setPatient(patient);
        repository.save(user);

        return respondWithMessage(showInfo(),
                "Cập nhật thông tin cá nhân thành công.");
    }

    private UserResponse respondWithMessage(UserResponse response, String message) {
        return UserResponse.builder()
                .fullName(response.getFullName())
                .email(response.getEmail())
                .phone(response.getPhone())
                .gender(response.getGender())
                .address(response.getAddress())
                .sessions(response.getSessions())
                .message(message)
                .build();
    }
}

package project.clinic.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.clinic.auth.AuthenticationResponse;
import project.clinic.controller.MainResponse;
import project.clinic.controller.doctor.DoctorResponse;
import project.clinic.model.Doctor;
import project.clinic.model.Role;
import project.clinic.model.Session;
import project.clinic.model.User;
import project.clinic.repository.DoctorRepository;
import project.clinic.repository.SessionRepository;
import project.clinic.repository.SpecRepository;
import project.clinic.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final DoctorRepository doctorRepository;

    private final SpecRepository specRepository;

    private final SessionRepository sessionRepository;

    private final PasswordEncoder passwordEncoder;

    public MainResponse lockAccount(Long id, LockAcountIssue issue) {
        User user = userRepository.findUserById(id);

        user.setEnabled(false);
        user.setLocked(issue.getReason());

        return MainResponse.builder()
                .message("Tài khoản với id " + id + " đã bị khóa.")
                .build();
    }

    public MainResponse unlockAccount(Long id) {
        User user = userRepository.findUserById(id);

        user.setEnabled(true);

        return MainResponse.builder()
                .message("Tài khoản với id " + id + " đã được mở khóa.")
                .build();
    }

    public AuthenticationResponse registerDoctor(DoctorRegisterRequest request) {

        var doctor = Doctor.builder()
                .phone(request.getPhone())
                .name(request.getName())
                .description(request.getDescription())
                .specialization(specRepository.findByName(request.getSpecialization()))
                .build();

        doctorRepository.save(doctor);

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createAt(String.valueOf(new Date(System.currentTimeMillis())))
                .fullName(request.getName())
                .gender(request.getGender())
                .phone(request.getPhone())
                .doctor(doctor)
                .role(Role.DOCTOR)
                .build();

        userRepository.save(user);

        return AuthenticationResponse.builder()
                .message("Lưu thông tin bác sĩ thành công.")
                .build();
    }

    public DoctorResponse getAllSessions() {
        List<Session> list = sessionRepository.findAll();

        return DoctorResponse.builder()
                .sessions(list)
                .message("Danh sách lịch khám bệnh đã đăng ký")
                .build();
    }

    public DoctorSearchResponse getAllDoctors() {

        return DoctorSearchResponse.builder()
                .doctors(doctorRepository.findAll())
                .message("Danh sách bác sĩ trong hệ thống.")
                .build();
    }

    public DoctorResponse getDoctorSessionList(Long id) {

        Doctor doctor = doctorRepository.findDoctorById(id);
        List<Session> sessions = sessionRepository.findPatientsByDoctorId(doctor.getId());

        return DoctorResponse.builder()
                .sessions(sessions)
                .message("Danh sách bệnh nhân của bác sĩ " + doctor.getName())
                .build();
    }
}

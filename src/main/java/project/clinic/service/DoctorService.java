package project.clinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.clinic.controller.doctor.CancelRequest;
import project.clinic.controller.doctor.DoctorResponse;
import project.clinic.model.Doctor;
import project.clinic.model.Session;
import project.clinic.model.User;
import project.clinic.repository.DoctorRepository;
import project.clinic.repository.SessionRepository;
import project.clinic.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final SessionRepository sessionRepository;

    private final DoctorRepository doctorRepository;

    private final UserRepository userRepository;


    public DoctorResponse getPatients() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findUserByEmail(email).orElseThrow();
        Doctor doctor = doctorRepository.findDoctorByName(user.getFullName());

        List<Session> list = sessionRepository.findPatientsByDoctorId(doctor.getId());

        return DoctorResponse.builder()
                .sessions(list)
                .message("Danh sách bệnh nhân.")
                .build();
    }

    public DoctorResponse cancelSession(Long id, CancelRequest request) {
        Session session = sessionRepository.findById(id).orElseThrow();

        session.setStatus("Cancelled");
        session.setReason(request.getReason());
        sessionRepository.save(session);

        return DoctorResponse.builder()
                .message("Hủy lịch khám thành công.")
                .build();
    }

    public DoctorResponse getAll() {
        return DoctorResponse.builder()
                .sessions(sessionRepository.findAll())
                .build();
    }
}

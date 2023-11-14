package project.clinic.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.clinic.controller.MainResponse;
import project.clinic.controller.session.SessionRequest;
import project.clinic.controller.session.SessionResponse;
import project.clinic.model.*;
import project.clinic.repository.*;

import java.util.List;

@Service
public class MainService {

    private final ClinicRepository clinicRepository;
    private final SpecRepository specRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public MainService(ClinicRepository clinicRepository,
                       SpecRepository specRepository,
                       DoctorRepository doctorRepository,
                       UserRepository userRepository,
                       SessionRepository sessionRepository) {
        this.clinicRepository = clinicRepository;
        this.specRepository = specRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public MainResponse getMainPage() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Clinic> clinics = clinicRepository.findAll();
        List<Specialization> specializations = specRepository.findAll();

        return MainResponse.builder()
                .doctors(doctors)
                .clinics(clinics)
                .specializations(specializations)
                .message("Đây là trang chủ.")
                .build();
    }

    public MainResponse searchPrice(String keyword) {
        int price = Integer.parseInt(keyword);
        List<Clinic> clinics = clinicRepository.findClinicsByPrice(price);

        return MainResponse.builder()
                .clinics(clinics)
                .message("Danh sách phòng khám, bệnh viện có mức giá thấp hơn hoặc tương tự mức giá được tìm kiếm")
                .build();
    }

    public MainResponse searchClinic(String keyword) {
        List<Clinic> clinics = clinicRepository.findClinicsByKeyword(keyword);

        return MainResponse.builder()
                .clinics(clinics)
                .message("Danh sách phòng khám, bệnh viện có chứa từ khóa.")
                .build();
    }

    public MainResponse searchDoctor(String keyword) {

        List<Doctor> doctors = doctorRepository.findDoctorsBySpec(keyword);
        List<Clinic> clinics = clinicRepository.findClinicsBySpec(keyword);

        return MainResponse.builder()
                .doctors(doctors)
                .clinics(clinics)
                .message("Danh sách bác sĩ, phòng khám, bệnh viện thuộc chuyên khoa được tìm kiếm.")
                .build();
    }

    public SessionResponse createSession(SessionRequest request) {

        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email).orElseThrow();

        var session = Session.builder()
                .price(request.getPrice())
                .time(request.getTime())
                .doctor(doctorRepository.findDoctorById(request.getDoctor()))
                .patient(user.getPatient())
                .status("Pending")
                .build();

        sessionRepository.save(session);

        return SessionResponse.builder()
                .message("Đăng ký lịch khám thành công, đây là thông tin đã đăng ký")
                .session(session)
                .build();
    }
}

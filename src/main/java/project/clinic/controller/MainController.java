package project.clinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.clinic.auth.AuthenticationResponse;
import project.clinic.auth.AuthenticationService;
import project.clinic.auth.LoginRequest;
import project.clinic.controller.session.SessionRequest;
import project.clinic.controller.session.SessionResponse;
import project.clinic.service.MainService;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final AuthenticationService authService;

    private final MainService mainService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

//    private void login(String username) {
//        // Check user
//
//        // Authent
//    }

    @GetMapping("/main")
    public ResponseEntity<MainResponse> mainPage() {
        return ResponseEntity.ok(mainService.getMainPage());
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchPage() {
        return ResponseEntity.ok("Chọn tiêu chí cần tìm kiếm: c = phòng khám, p = mức giá, s = chuyên khoa.");
    }

    @GetMapping("/search/p")
    public ResponseEntity<MainResponse> searchPrice(@RequestParam("price") String price) {
        return ResponseEntity.ok(mainService.searchPrice(price));
    }

    @GetMapping("/search/c")
    public ResponseEntity<MainResponse> searchClinic(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(mainService.searchClinic(keyword));
    }

    @GetMapping("/search/s")
    public ResponseEntity<MainResponse> searchDoctorBySpecialization(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(mainService.searchDoctor(keyword));
    }

    @GetMapping("/session")
    public ResponseEntity<String> session() {
        return ResponseEntity.ok("Tạo mới một session để đặt lịch khám");
    }

    @PostMapping("/session/new")
    public ResponseEntity<SessionResponse> newSession(@RequestBody SessionRequest request) {
        return ResponseEntity.ok(mainService.createSession(request));
    }
}

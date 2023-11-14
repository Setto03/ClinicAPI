package project.clinic.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.clinic.auth.AuthenticationResponse;
import project.clinic.controller.MainResponse;
import project.clinic.controller.doctor.DoctorResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @RequestMapping(value = "/users/lock/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MainResponse> lockAccount(@PathVariable Long id,
                                                    @RequestBody LockAcountIssue issue) {
        return ResponseEntity.ok(adminService.lockAccount(id, issue));
    }

    @RequestMapping(value = "/users/unlock/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MainResponse> unlockAccount(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.unlockAccount(id));
    }

    @PostMapping("/new-doctor")
    public ResponseEntity<AuthenticationResponse> registerDoctor(@RequestBody DoctorRegisterRequest request) {
        return ResponseEntity.ok(adminService.registerDoctor(request));
    }

    @GetMapping("/sessions")
    public ResponseEntity<DoctorResponse> sessionList() {
        return ResponseEntity.ok(adminService.getAllSessions());
    }

    @GetMapping("/doctor")
    public ResponseEntity<DoctorSearchResponse> doctorList() {
        return ResponseEntity.ok(adminService.getAllDoctors());
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<DoctorResponse> getDoctorSessionList(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getDoctorSessionList(id));
    }
}

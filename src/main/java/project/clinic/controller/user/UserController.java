package project.clinic.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.clinic.auth.AuthenticationResponse;
import project.clinic.controller.session.SessionRequest;
import project.clinic.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/password")
    public ResponseEntity<String> requestPasswordReset() {
        return ResponseEntity.ok("Nhập email cần thay đổi mật khẩu");
    }

    @PostMapping("/password/request")
    public ResponseEntity<AuthenticationResponse> sendRequest(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(service.checkEmail(request));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody ResetPassword request) {
        return ResponseEntity.ok(service.resetPassword(request));
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> showInfo() {
        return ResponseEntity.ok(service.showInfo());
    }

    @PostMapping("/info/profile")
    public ResponseEntity<UserResponse> updatePatientProfile(@RequestBody UserPatientProfile profile) {
        return ResponseEntity.ok(service.updateProfile(profile));
    }


    @RequestMapping(value = "/info/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserResponse> updateSession(@RequestBody SessionRequest request,
                                                      @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.updateSession(id, request));
    }
}

package project.clinic.controller.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.clinic.service.DoctorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/list")
    public ResponseEntity<DoctorResponse> getPatients() {
        return ResponseEntity.ok(doctorService.getPatients());
    }

    @GetMapping("/all")
    public ResponseEntity<DoctorResponse> getAllPatients() {
        return ResponseEntity.ok(doctorService.getAll());
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<DoctorResponse> cancelSession(@PathVariable Long id,
                                                        @RequestBody CancelRequest request) {
        return ResponseEntity.ok(doctorService.cancelSession(id, request));
    }
}

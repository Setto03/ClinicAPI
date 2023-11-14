package project.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clinic.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}

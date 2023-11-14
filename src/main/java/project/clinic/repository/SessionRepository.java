package project.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.clinic.model.Session;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("select s from Session s where s.doctor.id = ?1")
    List<Session> findPatientsByDoctorId(Long id);

    @Query("select s from Session s where s.patient.name like %?1%")
    List<Session> findSessionsByPatientName(String name);

}

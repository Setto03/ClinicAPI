package project.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.clinic.model.Doctor;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("select d from Doctor d where d.specialization.name like %?1%")
    public List<Doctor> findDoctorsBySpec(String keyword);

    @Query("select d from Doctor d where d.name like %?1%")
    Doctor findDoctorByName(String name);

    public Doctor findDoctorById(Long id);
}

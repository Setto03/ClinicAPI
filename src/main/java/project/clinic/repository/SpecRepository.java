package project.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.clinic.model.Specialization;


@Repository
public interface SpecRepository extends JpaRepository<Specialization, Long> {

    @Query("select s from Specialization s where s.name like %?1% ")
    public Specialization findByName(String keyword);
}

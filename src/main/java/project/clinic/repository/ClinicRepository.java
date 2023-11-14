package project.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.clinic.model.Clinic;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    @Query("select c from Clinic c where c.price <= ?1")
    public List<Clinic> findClinicsByPrice(int price);

    @Query("select c from Clinic c where c.name like %?1% " +
            "or c.description like %?1% " +
            "or c.address like %?1% ")
    public List<Clinic> findClinicsByKeyword(String keyword);

    @Query("select c from Clinic c where c.specialization.name like %?1%")
    public List<Clinic> findClinicsBySpec(String keyword);
}

package coronaManagement.domain.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.id = :personId and p.vaccinationCount >= 1")
    Optional<VaccinationPerson> findPersonWhoVaccinationAtLeastOnce(@Param("personId") Long personId);

    @Query("select p from Person p where p.id = :personId and p.physicalStatus = PhysicalStatus.INFECTED")
    Optional<InfectedPerson> findPersonWhoInfectedButNotInHospital(@Param("personId") Long personId);
}

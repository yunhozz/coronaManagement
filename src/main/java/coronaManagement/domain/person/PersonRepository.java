package coronaManagement.domain.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    //재접종 대상자 검색
    @Query("select p from Person p where p.vaccinationCount < :nextVaccinationCount and p.vaccinationCount > 0")
    List<VaccinationPerson> findPeopleWhoMustVaccination(@Param("nextVaccinationCount") int nextVaccinationCount);

    //재접종 가능 여부 검색
    @Query("select p from Person p where p.id = :personId and p.vaccinationCount >= 1")
    Optional<VaccinationPerson> findPersonWhoCanReVaccination(@Param("personId") Long personId);
}

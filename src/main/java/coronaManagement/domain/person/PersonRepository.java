package coronaManagement.domain.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository<T extends Person> extends JpaRepository<T, Long>, PersonRepositoryCustom {

    //백신 재접종 대상자 검색
    @Query("select p from Person p where p.vaccinationCount < :nextVaccinationCount and p.vaccinationCount > 0")
    List<T> findPeopleWhoMustReVaccination(@Param("nextVaccinationCount") int nextVaccinationCount);

    //백신 재접종 가능한 사람인지 검색
    @Query("select p from Person p where p.id = :personId and p.vaccinationCount > 0")
    Optional<T> findPersonWhoCanReVaccination(@Param("personId") Long personId);
}

package coronaManagement.domain.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.vaccinationCount < :vaccinationCount and p.vaccinationCount > 0")
    List<Person> findPersonWhoVaccinationTarget(@Param("vaccinationCount") int vaccinationCount);
}

package coronaManagement.repo;

import coronaManagement.domain.person.Person;
import coronaManagement.domain.person.PersonWhoVaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where :date - p.vaccinationDate > 180")
    List<PersonWhoVaccination> findVaccinationAgain(@Param("date") LocalDateTime localDateTime);
}

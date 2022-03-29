package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}

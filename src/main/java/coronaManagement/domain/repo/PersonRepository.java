package coronaManagement.domain.repo;

import coronaManagement.domain.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}

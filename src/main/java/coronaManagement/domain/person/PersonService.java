package coronaManagement.domain.person;

import coronaManagement.domain.hospital.repo.HospitalRepository;
import coronaManagement.domain.person.dto.PersonRequest;
import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.domain.virus.VirusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final VaccineRepository vaccineRepository;
    private final VirusRepository virusRepository;
    private final HospitalRepository hospitalRepository;

    public Long saveVaccinationPerson(PersonRequest personRequest) {
        Person person = personRequest.vaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveNotVaccinationPerson(PersonRequest personRequest) {
        Person person = personRequest.notVaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveInfectedPerson(PersonRequest personRequest) {
        Person person = personRequest.infectedPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveContactedPerson(PersonRequest personRequest) {
        Person person = personRequest.contactedPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public void reVaccination(Long personId) throws Exception {
        Optional<VaccinationPerson> findPerson = personRepository.findPersonWhoCanReVaccination(personId);

        if (findPerson.isEmpty()) {
            throw new IllegalStateException("This person can't be re vaccinated.");
        }

        VaccinationPerson vaccinationPerson = findPerson.get();
        vaccinationPerson.reVaccination();
    }



    @Transactional(readOnly = true)
    public List<VaccinationPerson> findReVaccinationPeople(int nextVaccinationCount) {
        return personRepository.findPeopleWhoMustReVaccination(nextVaccinationCount);
    }

    @Transactional(readOnly = true)
    public Person findPerson(Long personId) throws Throwable {
        return (Person) personRepository.findById(personId)
                .orElseThrow(() -> new IllegalStateException("Can't find person."));
    }

    @Transactional(readOnly = true)
    public List<Person> findPeople() {
        return personRepository.findAll();
    }
}

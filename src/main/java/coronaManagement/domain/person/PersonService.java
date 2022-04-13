package coronaManagement.domain.person;

import coronaManagement.domain.hospital.repo.HospitalRepository;
import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.domain.virus.VirusRepository;
import coronaManagement.global.dto.request.PersonRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final VaccineRepository vaccineRepository;
    private final VirusRepository virusRepository;
    private final HospitalRepository hospitalRepository;

    public Long saveVaccinationPerson(PersonRequestDto personRequestDto) {
        Person person = personRequestDto.vaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveNotVaccinationPerson(PersonRequestDto personRequestDto) {
        Person person = personRequestDto.notVaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveInfectedPerson(PersonRequestDto personRequestDto) {
        Person person = personRequestDto.infectedPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public void

    public Long saveContactedPerson(PersonRequestDto personRequestDto) {
        Person person = personRequestDto.contactedPersonToEntity();
        personRepository.save(person);

        return person.getId();
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

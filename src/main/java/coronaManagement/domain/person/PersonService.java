package coronaManagement.domain.person;

import coronaManagement.domain.hospital.Hospital;
import coronaManagement.domain.hospital.HospitalRepository;
import coronaManagement.global.dto.PersonDto;
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
    private final HospitalRepository hospitalRepository;

    public Long saveVaccinationPerson(PersonDto personDto) {
        Person vaccinationPerson = personDto.vaccinationPersonToEntity();
        personRepository.save(vaccinationPerson);

        return vaccinationPerson.getId();
    }

    public Long saveNotVaccinationPerson(PersonDto personDto) {
        Person notVaccinationPerson = personDto.notVaccinationPersonToEntity();
        personRepository.save(notVaccinationPerson);

        return notVaccinationPerson.getId();
    }

    public Long saveInfectedPerson(PersonDto personDto) {
        Person infectedPerson = personDto.infectedPersonToEntity();
        personRepository.save(infectedPerson);

        return infectedPerson.getId();
    }

    public Long saveContactedPerson(PersonDto personDto) {
        Person contactedPerson = personDto.contactedPersonToEntity();
        personRepository.save(contactedPerson);

        return contactedPerson.getId();
    }

    //감염 처리
    public void getInfection(Long personId) {

    }

    //백신 재접종 대상자 검색
    @Transactional(readOnly = true)
    public List<VaccinationPerson> findReVaccinationPerson(int nextVaccinationCount) {
        return personRepository.findPeopleWhoMustBeVaccination(nextVaccinationCount);
    }

    //백신 재접종
    public void reVaccination(Long personId) {
        Optional<VaccinationPerson> findPerson = personRepository.findPersonWhoCanReVaccination(personId);

        if (findPerson.isEmpty()) {
            throw new IllegalStateException("This person is not allowed.");
        }

        VaccinationPerson vaccinationPerson = findPerson.get();
        vaccinationPerson.reVaccination();
    }

    //회복
    public void recover(Long personId) {

    }

    @Transactional(readOnly = true)
    public Person findPeople(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new IllegalStateException("Can't find person."));
    }

    @Transactional(readOnly = true)
    public List<Person> findPerson() {
        return personRepository.findAll();
    }
}

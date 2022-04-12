package coronaManagement.domain.person;

import coronaManagement.domain.hospital.repo.HospitalRepository;
import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.global.dto.request.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final HospitalRepository hospitalRepository;

    public Long saveVaccinationPerson(PersonDto personDto) {
        Person person = personDto.vaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveNotVaccinationPerson(PersonDto personDto) {
        Person person = personDto.notVaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveInfectedPerson(PersonDto personDto) {
        Person person = personDto.infectedPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveContactedPerson(PersonDto personDto) {
        Person person = personDto.contactedPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    //백신 재접종 대상자 검색
    @Transactional(readOnly = true)
    public List<Person> findReVaccinationPerson(int nextVaccinationCount) {

    }

    //백신 재접종
    public void reVaccination(Long personId) {

    }

    //감염 상태에서 회복
    public void recover(Long personId) {

    }

    //감염 상태에서 죽음
    public void passAway(Long personId) {

    }

    //백신 접종자 감염 처리
    public void getInfectionForV(Long personId) {

    }

    //백신 미접종자 감염 처리
    public void getInfectionForNV(Long personId) {

    }

    //밀접 접촉자 감염 처리
    public void getInfectionForC(Long personId) {

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

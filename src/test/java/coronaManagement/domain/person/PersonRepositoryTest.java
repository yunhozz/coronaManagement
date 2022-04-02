package coronaManagement.domain.person;

import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.global.dto.PersonDto;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired PersonRepository personRepository;
    @Autowired VaccineRepository vaccineRepository;

    @Test
    public void findPersonWhoVaccination() {
        //given
        Vaccine vaccine = new Vaccine("vac", "doctor", 123);
        vaccineRepository.save(vaccine);

        PersonDto personDto = new PersonDto();
        personDto.setName("yunho");
        personDto.setCity(City.SEOUL);
        personDto.setGender(Gender.MALE);
        personDto.setAge(27);
        personDto.setPhoneNumber(01033317551);
        personDto.setVaccine(vaccine);

        //when
        Person vaccinationPerson = personDto.vaccinationPersonToEntity();
        Person savedPerson = personRepository.save(vaccinationPerson);
        VaccinationPerson findPerson = personRepository.findPersonWhoVaccination(savedPerson.getId()).get();

        //then
        assertThat(findPerson).isEqualTo(savedPerson);
    }
}
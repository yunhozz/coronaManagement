package coronaManagement.domain.person;

import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.global.dto.PersonDto;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PersonRepositoryTest {

    @Autowired PersonRepository personRepository;
    @Autowired VaccineRepository vaccineRepository;

    @Test
    public void findPeopleWhoMustBeVaccination() {

    }

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

        Person savedPerson = personRepository.save(personDto.vaccinationPersonToEntity());

        //when
        VaccinationPerson findPerson = personRepository.findPersonWhoVaccination(savedPerson.getId()).get();

        //then
        assertThat(findPerson.getName()).isEqualTo("yunho");
        assertThat(findPerson.getVaccinationCount()).isEqualTo(1);
        assertThat(findPerson.getInfectionStatus()).isEqualTo(InfectionStatus.INFECTED);
    }
}
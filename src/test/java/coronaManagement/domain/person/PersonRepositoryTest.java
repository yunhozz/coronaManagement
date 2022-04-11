package coronaManagement.domain.person;

import coronaManagement.domain.record.EachRecordRepository;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.global.dto.EachRecordDto;
import coronaManagement.global.dto.PersonDto;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired PersonRepository personRepository;
    @Autowired VaccineRepository vaccineRepository;
    @Autowired EachRecordRepository eachRecordRepository;

    @Test
    void findPeopleWhoMustBeVaccination() {
        //given


        //when


        //then
    }

    @Test
    void findPersonWhoVaccination() {
        //given
        Vaccine vaccine = new Vaccine("vac", "doctor", 123);
        vaccineRepository.save(vaccine);

        EachRecordDto eachRecordDto = new EachRecordDto();
        eachRecordRepository.save(eachRecordDto.toEntity());

        PersonDto personDto = new PersonDto();
        personDto.setName("yunho");
        personDto.setCity(City.SEOUL);
        personDto.setGender(Gender.MALE);
        personDto.setAge(27);
        personDto.setPhoneNumber(01033317551);
        personDto.setVaccine(vaccine);
        personDto.setEachRecord(eachRecordDto.toEntity());

        Person savedPerson = personRepository.save(personDto.vaccinationPersonToEntity());

        //when

        //then

    }
}
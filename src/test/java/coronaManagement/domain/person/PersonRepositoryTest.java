package coronaManagement.domain.person;

import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.EachRecordRepository;
import coronaManagement.domain.record.TotalRecord;
import coronaManagement.domain.record.TotalRecordRepository;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.global.dto.EachRecordDto;
import coronaManagement.global.dto.PersonDto;
import coronaManagement.global.dto.TotalRecordDto;
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
class PersonRepositoryTest {

    @Autowired PersonRepository personRepository;
    @Autowired VaccineRepository vaccineRepository;
    @Autowired EachRecordRepository eachRecordRepository;
    @Autowired TotalRecordRepository totalRecordRepository;

    @Test
    void findPeopleWhoMustReVaccination() {
        //given


        //when


        //then
    }

    @Test
    @Rollback(value = false)
    void findPersonWhoCanReVaccination() {
        //given
        Vaccine vaccine = new Vaccine("vac", "doctor", 123);
        vaccineRepository.save(vaccine);

        TotalRecordDto totalRecordDto = new TotalRecordDto();
        TotalRecord totalRecord = totalRecordRepository.save(totalRecordDto.toEntity());

        EachRecordDto eachRecordDto = new EachRecordDto();
        eachRecordDto.setYear(2022);
        eachRecordDto.setMonth(4);
        eachRecordDto.setDay(11);
        eachRecordDto.setTotalRecord(totalRecord);
        EachRecord eachRecord = eachRecordRepository.save(eachRecordDto.toEntity());

        PersonDto personDto = new PersonDto();
        personDto.setName("yunho");
        personDto.setCity(City.SEOUL);
        personDto.setGender(Gender.MALE);
        personDto.setAge(27);
        personDto.setPhoneNumber("01033317551");
        personDto.setVaccine(vaccine);
        personDto.setEachRecord(eachRecord);

        Person savedPerson = personRepository.save(personDto.vaccinationPersonToEntity());

        //when
        VaccinationPerson vaccinationPerson = personRepository.findPersonWhoCanReVaccination(savedPerson.getId()).get();

        //then
        assertThat(vaccinationPerson.getName()).isEqualTo("yunho");
        assertThat(vaccinationPerson.getVaccinationCount()).isEqualTo(1);
        assertThat(vaccinationPerson.getInfectionStatus()).isEqualTo(InfectionStatus.BEFORE_INFECT);

        assertThat(vaccinationPerson.getVaccine().getName()).isEqualTo("vac");
        assertThat(vaccinationPerson.getVaccine().getStockQuantity()).isEqualTo(122);

        assertThat(vaccinationPerson.getEachRecord().getTodayVaccination()).isEqualTo(1);
        assertThat(vaccinationPerson.getEachRecord().getTotalRecord().getTotalVaccination()).isEqualTo(1);
    }
}
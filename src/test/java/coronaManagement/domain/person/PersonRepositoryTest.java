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

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired PersonRepository personRepository;
    @Autowired VaccineRepository vaccineRepository;
    @Autowired EachRecordRepository eachRecordRepository;
    @Autowired TotalRecordRepository totalRecordRepository;

    @Test
    @Rollback(value = false)
    void findPeopleWhoMustReVaccination() {
        //given
        Vaccine vaccine = createVaccine();
        EachRecord eachRecord = createRecord();

        PersonDto personDto1 = new PersonDto();
        personDto1.setName("yunho1");
        personDto1.setCity(City.SEOUL);
        personDto1.setGender(Gender.MALE);
        personDto1.setAge(27);
        personDto1.setPhoneNumber("01033317551");
        personDto1.setVaccine(vaccine);
        personDto1.setEachRecord(eachRecord);
        personRepository.save(personDto1.vaccinationPersonToEntity());

        PersonDto personDto2 = new PersonDto();
        personDto2.setName("yunho2");
        personDto2.setCity(City.BUSAN);
        personDto2.setGender(Gender.FEMALE);
        personDto2.setAge(28);
        personDto2.setPhoneNumber("01012345678");
        personDto2.setVaccine(vaccine);
        personDto2.setEachRecord(eachRecord);
        personRepository.save(personDto2.vaccinationPersonToEntity());

        //when
        List<VaccinationPerson> result = personRepository.findPeopleWhoMustReVaccination(2);

        //then
    }

    @Test
    void findPersonWhoCanReVaccination() {
        //given
        Vaccine vaccine = createVaccine();
        EachRecord eachRecord = createRecord();

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

    private Vaccine createVaccine() {
        Vaccine vaccine = new Vaccine("vac", "doctor", 123);
        vaccineRepository.save(vaccine);

        return vaccine;
    }

    private EachRecord createRecord() {
        TotalRecordDto totalRecordDto = new TotalRecordDto();
        TotalRecord totalRecord = totalRecordRepository.save(totalRecordDto.toEntity());

        EachRecordDto eachRecordDto = new EachRecordDto();
        eachRecordDto.setYear(2022);
        eachRecordDto.setMonth(4);
        eachRecordDto.setDay(11);
        eachRecordDto.setTotalRecord(totalRecord);

        return eachRecordRepository.save(eachRecordDto.toEntity());
    }
}
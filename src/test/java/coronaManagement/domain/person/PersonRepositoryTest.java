package coronaManagement.domain.person;

import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.EachRecordRepository;
import coronaManagement.domain.record.TotalRecord;
import coronaManagement.domain.record.TotalRecordRepository;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.global.dto.request.EachRecordRequestDto;
import coronaManagement.global.dto.request.PersonRequestDto;
import coronaManagement.global.dto.request.TotalRecordRequestDto;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    void findPeopleWhoMustReVaccination() {
        //given
        Vaccine vaccine = createVaccine();
        EachRecord eachRecord = createRecord();
        Person person1 = createPerson("yunho1", City.SEOUL, Gender.MALE, 27, "01033317551", vaccine, eachRecord);
        Person person2 = createPerson("yunho2", City.BUSAN, Gender.FEMALE, 28, "01012345678", vaccine, eachRecord);

        //when
        VaccinationPerson person2ToVp = (VaccinationPerson) person2;
        person2ToVp.reVaccination(); //person1 1차, person2 2차 접종 (update)

        List<VaccinationPerson> findPeople = personRepository.findPeopleWhoMustReVaccination(2); //2차 접종 대상자

        //then
        assertThat(findPeople.size()).isEqualTo(1);
        assertThat(findPeople.get(0).getId()).isEqualTo(person1.getId());
        assertThat(findPeople.get(0).getName()).isEqualTo("yunho1");
        assertThat(findPeople.get(0).getVaccinationCount()).isEqualTo(1);

        assertThat(vaccine.getName()).isEqualTo("vac");
        assertThat(vaccine.getStockQuantity()).isEqualTo(7);

        assertThat(eachRecord.getTodayVaccination()).isEqualTo(2);
        assertThat(eachRecord.getTotalRecord().getTotalVaccination()).isEqualTo(2);
    }

    @Test
    void findPersonWhoCanReVaccination() {
        //given
        Vaccine vaccine = createVaccine();
        EachRecord eachRecord = createRecord();
        Person savedPerson = createPerson("yunho", City.SEOUL, Gender.MALE, 27, "01033317551", vaccine, eachRecord);

        //when
        VaccinationPerson vaccinationPerson = (VaccinationPerson) personRepository.findPersonWhoCanReVaccination(savedPerson.getId()).get();

        //then
        assertThat(vaccinationPerson.getId()).isEqualTo(savedPerson.getId());
        assertThat(vaccinationPerson.getName()).isEqualTo("yunho");
        assertThat(vaccinationPerson.getVaccinationCount()).isEqualTo(1);
        assertThat(vaccinationPerson.getInfectionStatus()).isEqualTo(InfectionStatus.BEFORE_INFECT);

        assertThat(vaccinationPerson.getVaccine().getName()).isEqualTo("vac");
        assertThat(vaccinationPerson.getVaccine().getStockQuantity()).isEqualTo(9);

        assertThat(vaccinationPerson.getEachRecord().getTodayVaccination()).isEqualTo(1);
        assertThat(vaccinationPerson.getEachRecord().getTotalRecord().getTotalVaccination()).isEqualTo(1);
    }

    @Test
    void findVpWithVaccine() {
        //given
        Vaccine vaccine = createVaccine();
        EachRecord eachRecord = createRecord();
        Person person1 = createPerson("yunho1", City.SEOUL, Gender.MALE, 27, "01033317551", vaccine, eachRecord);
        Person person2 = createPerson("yunho2", City.BUSAN, Gender.FEMALE, 28, "01012345678", vaccine, eachRecord);

        //when
        List<VaccinationPerson> result = personRepository.findVpWithVaccine();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getVaccine().getName()).isEqualTo("vac");
    }

    @Test
    void findAllWithContactedPerson() {
        //given
        Vaccine vaccine = createVaccine();
        EachRecord eachRecord = createRecord();
        Person person1 = createPerson("yunho1", City.SEOUL, Gender.MALE, 27, "01033317551", vaccine, eachRecord);
        Person person2 = createPerson("yunho2", City.BUSAN, Gender.FEMALE, 28, "01012345678", vaccine, eachRecord);

        //when
        List<ContactedPerson> result = personRepository.findAllWithContactedPerson(0, 2);

        //then
        assertThat(result.size()).isEqualTo(0); //Data is empty so result's size is zero.
    }

    private Person createPerson(String name, City city, Gender gender, int age, String phoneNumber, Vaccine vaccine, EachRecord eachRecord) {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setName(name);
        personRequestDto.setCity(city);
        personRequestDto.setGender(gender);
        personRequestDto.setAge(age);
        personRequestDto.setPhoneNumber(phoneNumber);
        personRequestDto.setVaccine(vaccine);
        personRequestDto.setEachRecord(eachRecord);

        return (Person) personRepository.save(personRequestDto.vaccinationPersonToEntity());
    }

    private Vaccine createVaccine() {
        Vaccine vaccine = new Vaccine("vac", "doctor", 10);
        vaccineRepository.save(vaccine);

        return vaccine;
    }

    private EachRecord createRecord() {
        TotalRecordRequestDto totalRecordRequestDto = new TotalRecordRequestDto();
        TotalRecord totalRecord = totalRecordRepository.save(totalRecordRequestDto.toEntity());

        EachRecordRequestDto eachRecordRequestDto = new EachRecordRequestDto();
        eachRecordRequestDto.setYear(2022);
        eachRecordRequestDto.setMonth(4);
        eachRecordRequestDto.setDay(11);
        eachRecordRequestDto.setTotalRecord(totalRecord);

        return eachRecordRepository.save(eachRecordRequestDto.toEntity());
    }
}
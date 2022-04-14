package coronaManagement.domain.person;

import coronaManagement.domain.hospital.Hospital;
import coronaManagement.domain.hospital.repo.HospitalRepository;
import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.EachRecordRepository;
import coronaManagement.domain.record.TotalRecord;
import coronaManagement.domain.record.TotalRecordRepository;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.domain.virus.Virus;
import coronaManagement.domain.virus.VirusRepository;
import coronaManagement.global.dto.request.EachRecordRequestDto;
import coronaManagement.global.dto.request.PersonRequestDto;
import coronaManagement.global.dto.request.TotalRecordRequestDto;
import coronaManagement.global.enums.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired PersonRepository personRepository;
    @Autowired VaccineRepository vaccineRepository;
    @Autowired VirusRepository virusRepository;
    @Autowired HospitalRepository hospitalRepository;
    @Autowired EachRecordRepository eachRecordRepository;
    @Autowired TotalRecordRepository totalRecordRepository;

    @Test
    void findPeopleWhoMustReVaccination() throws Exception {
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
    void findPersonWhoCanReVaccination() throws Exception {
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
    @Rollback(value = false)
    void findPageBy() throws Exception {
        //given
        createPerson("yunho1", City.SEOUL , Gender.MALE, 27);
        createPerson("yunho2", City.SEOUL , Gender.FEMALE, 27);
        createPerson("yunho3", City.BUSAN , Gender.MALE, 37);
        createPerson("yunho4", City.BUSAN , Gender.FEMALE, 17);
        createPerson("yunho5", City.INCHEON , Gender.MALE, 17);
        createPerson("yunho6", City.INCHEON , Gender.FEMALE, 37);

        City city = City.SEOUL;
        Gender gender = Gender.MALE;
        int age = 27;

        //when
        PageRequest pageRequest1 = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "city"));
        PageRequest pageRequest2 = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "gender"));
        PageRequest pageRequest3 = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "age"));

        Page<Person> pageByCity = personRepository.findPageByCity(city, pageRequest1);
        Page<Person> pageByGender = personRepository.findPageByGender(gender, pageRequest2);
        Page<Person> pageByAge = personRepository.findPageByAge(age, pageRequest3);

        //then
        assertThat(pageByCity.getContent().size()).isEqualTo(3);
        assertThat(pageByGender.getContent().size()).isEqualTo(3);
        assertThat(pageByAge.getContent().size()).isEqualTo(3);

        for (Person person : pageByCity) {
            System.out.println("person.getName() = " + person.getName() + ", person.getCity() = " + person.getCity());
        }
    }

    @Test
    void findPeopleWithGenderAndAge() throws Exception {
        //given
        createPerson("yunho1", City.SEOUL , Gender.MALE, 27);
        createPerson("yunho2", City.SEOUL , Gender.FEMALE, 27);
        createPerson("yunho3", City.BUSAN , Gender.MALE, 37);
        createPerson("yunho4", City.BUSAN , Gender.FEMALE, 17);
        createPerson("yunho5", City.INCHEON , Gender.MALE, 17);
        createPerson("yunho6", City.INCHEON , Gender.FEMALE, 37);

        //when
        List<Person> result = personRepository.findPeopleWithGenderAndAge(Gender.MALE, 27);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("yunho1");

        for (Person person : result) {
            System.out.println("name = " + person.getName() + ", gender = " + person.getGender() + ", age = " + person.getAge());
        }
    }

    @Test
    @Rollback(value = false)
    void findPeopleWhoInfectedAndHospitalized() throws Exception {
        //given
        Virus virus = createVirus();
        EachRecord eachRecord = createRecord();
        Hospital hospital = createHospital();

        InfectedPerson person1 = (InfectedPerson) createPerson("yunho1", virus, eachRecord);
        InfectedPerson person2 = (InfectedPerson) createPerson("yunho2", virus, eachRecord);
        InfectedPerson person3 = (InfectedPerson) createPerson("yunho3", virus, eachRecord);

        //when
        hospital.hospitalize(person1, person2); //person3을 제외한 입원 처리, Person - Hospital 연관관계 생성
        List<InfectedPerson> result = personRepository.findPeopleWhoInfectedAndHospitalized();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getVirus().getInfectionCount()).isEqualTo(3);
        assertThat(result.get(0).getEachRecord().getTodayInfection()).isEqualTo(3);
        assertThat(result.get(0).getHospital().getName()).isEqualTo("hos");
        assertThat(result.get(0).getHospital().getNumberOfBed()).isEqualTo(98);
        assertThat(result.contains(PhysicalStatus.INFECTED)).isFalse();

        for (InfectedPerson person : result) {
            System.out.println("person.name = " + person.getName() + ", hospital.name = " + person.getHospital().getName());
        }
    }

    @Test
    void findVpWithVaccine() throws Exception {
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
    void findAllWithContactedPerson() throws Exception {
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

    private Person createPerson(String name, City city, Gender gender, int age) {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setName(name);
        personRequestDto.setCity(city);
        personRequestDto.setGender(gender);
        personRequestDto.setAge(age);

        return (Person) personRepository.save(personRequestDto.notVaccinationPersonToEntity());
    }

    private Person createPerson(String name, Virus virus, EachRecord eachRecord) {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setName(name);
        personRequestDto.setVirus(virus);
        personRequestDto.setEachRecord(eachRecord);
        InfectedPerson infectedPerson = (InfectedPerson) personRepository.save(personRequestDto.infectedPersonToEntity());

        return infectedPerson;
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

    private Virus createVirus() {
        Virus virus = Virus.createVirus(VirusType.ALPHA, "China");
        virusRepository.save(virus);

        return virus;
    }

    private Hospital createHospital() {
        Hospital hospital = new Hospital("hos", 100);
        hospitalRepository.save(hospital);

        return hospital;
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
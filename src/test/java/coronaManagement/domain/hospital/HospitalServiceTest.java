package coronaManagement.domain.hospital;

import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.PersonService;
import coronaManagement.domain.person.dto.PersonRequest;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.TotalRecord;
import coronaManagement.domain.record.dto.EachRecordRequest;
import coronaManagement.domain.record.dto.TotalRecordRequest;
import coronaManagement.domain.routeInformation.dto.RouteInformationRequest;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.virus.Virus;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.PhysicalStatus;
import coronaManagement.global.enums.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class HospitalServiceTest {

    @Autowired private HospitalService hospitalService;
    @Autowired private PersonService personService;
    @Autowired EntityManager em;
    Vaccine vaccine;
    Virus virus;
    EachRecord eachRecord;

    @BeforeEach
    void beforeEach() {
        vaccine = createVaccine("vaccine", "developer", 100);
        virus = createVirus(VirusType.ALPHA, "China");

        EachRecordRequest eachRecordRequest = createEachRecordRequest(2022, 4, 19);
        eachRecord = eachRecordRequest.toEntity();
        eachRecord.updateField(createTotalRecord());

        em.persist(vaccine);
        em.persist(virus);
        em.persist(eachRecord);
    }

    @Test
    void makeHospital() throws Exception {
        //given
        HospitalForm hospitalForm1 = createHospitalForm("hosA", 100);
        HospitalForm hospitalForm2 = createHospitalForm("hosB", 200);

        //when
        hospitalService.makeHospital(hospitalForm1);
        hospitalService.makeHospital(hospitalForm2);
        List<Hospital> hospitals = hospitalService.findHospitals();

        //then
        assertThat(hospitals).extracting("name").containsExactly("hosA", "hosB");
        assertThat(hospitals).extracting("numberOfBed").containsExactly(100, 200);
    }

    @Test
    void makeHospitalFail() throws Exception {
        //given
        HospitalForm hospitalForm1 = createHospitalForm("hosA", 100);
        HospitalForm hospitalForm2 = createHospitalForm("hosA", 200);

        //when
        hospitalService.makeHospital(hospitalForm1);

        try {
            hospitalService.makeHospital(hospitalForm2);

        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("This hospital is already exist.");
            e.printStackTrace();
        }

        //then
    }

    @Test
    void hospitalize() throws Exception {
        //given
        PersonRequest personRequest1 = createPersonRequest("yunho1", City.SEOUL, Gender.MALE, 27, "111");
        PersonRequest personRequest2 = createPersonRequest("yunho2", City.BUSAN, Gender.MALE, 28, "222");
        HospitalForm hospitalForm = createHospitalForm("hos", 100);

        Long infectedPersonId1 = personService.saveInfectedPerson(personRequest1, virus.getId(), eachRecord.getId());
        Long infectedPersonId2 = personService.saveInfectedPerson(personRequest2, virus.getId(), eachRecord.getId());

        //when
        Long hospitalId = hospitalService.makeHospital(hospitalForm);
        Hospital hospital = hospitalService.findHospital(hospitalId);

        hospitalService.hospitalize(hospital.getId(), infectedPersonId1, infectedPersonId2);

        //then
        assertThat(hospital.getName()).isEqualTo("hos");
        assertThat(hospital.getNumberOfBed()).isEqualTo(98);
        assertThat(hospital.getInfectedPersonList()).extracting("name")
                .containsExactly("yunho1", "yunho2");
    }

    @Test
    void hospitalizeFail() throws Exception {
        //given
        PersonRequest personRequest1 = createPersonRequest("yunho1", City.SEOUL, Gender.MALE, 27, "111");
        PersonRequest personRequest2 = createPersonRequest("yunho2", City.BUSAN, Gender.MALE, 28, "222");
        PersonRequest personRequest3 = createPersonRequest("yunho3", City.INCHEON, Gender.MALE, 29, "333");
        HospitalForm hospitalForm = createHospitalForm("hos", 100);

        Long infectedPersonId1 = personService.saveInfectedPerson(personRequest1, virus.getId(), eachRecord.getId());
        Long infectedPersonId2 = personService.saveInfectedPerson(personRequest2, virus.getId(), eachRecord.getId());
        Long infectedPersonId3 = personService.saveInfectedPerson(personRequest3, virus.getId(), eachRecord.getId());
        Long hospitalId = hospitalService.makeHospital(hospitalForm);

        InfectedPerson infectedPerson1 = (InfectedPerson) personService.findPerson(infectedPersonId1).get();
        InfectedPerson infectedPerson2 = (InfectedPerson) personService.findPerson(infectedPersonId2).get();
        InfectedPerson infectedPerson3 = (InfectedPerson) personService.findPerson(infectedPersonId3).get();

        //when
        //person1 : 격리, person2 : 죽음, person3 : 감염(default)
        infectedPerson1.beIsolated();
        infectedPerson2.passedAway();

        Hospital hospital = hospitalService.findHospital(hospitalId);
        hospitalService.hospitalize(hospital.getId(), infectedPersonId1, infectedPersonId2, infectedPersonId3);

        //then
        assertThat(hospital.getName()).isEqualTo("hos");
        assertThat(hospital.getNumberOfBed()).isEqualTo(99);
        assertThat(hospital.getInfectedPersonList()).extracting("name")
                .containsExactly("yunho3");
    }

    @Test
    void completeTreatment() throws Exception {
        //given
        PersonRequest personRequest1 = createPersonRequest("yunho1", City.SEOUL, Gender.MALE, 27, "111");
        PersonRequest personRequest2 = createPersonRequest("yunho2", City.BUSAN, Gender.MALE, 28, "222");
        PersonRequest personRequest3 = createPersonRequest("yunho3", City.INCHEON, Gender.MALE, 29, "333");
        HospitalForm hospitalForm = createHospitalForm("hos", 100);

        Long infectedPersonId1 = personService.saveInfectedPerson(personRequest1, virus.getId(), eachRecord.getId());
        Long infectedPersonId2 = personService.saveInfectedPerson(personRequest2, virus.getId(), eachRecord.getId());
        Long infectedPersonId3 = personService.saveInfectedPerson(personRequest3, virus.getId(), eachRecord.getId());
        Long hospitalId = hospitalService.makeHospital(hospitalForm);

        InfectedPerson infectedPerson1 = (InfectedPerson) personService.findPerson(infectedPersonId1).get();
        InfectedPerson infectedPerson2 = (InfectedPerson) personService.findPerson(infectedPersonId2).get();
        InfectedPerson infectedPerson3 = (InfectedPerson) personService.findPerson(infectedPersonId3).get();

        //when
        Hospital hospital = hospitalService.findHospital(hospitalId);
        hospitalService.hospitalize(hospital.getId(), infectedPersonId1, infectedPersonId2, infectedPersonId3);

        hospitalService.completeTreatment(hospital.getId(), infectedPersonId1, infectedPersonId2);

        //then
        assertThat(hospital.getName()).isEqualTo("hos");
        assertThat(hospital.getNumberOfBed()).isEqualTo(99);
        assertThat(hospital.getInfectedPersonList()).extracting("name")
                .containsExactly("yunho3");

        assertThat(infectedPerson1.getPhysicalStatus()).isEqualTo(PhysicalStatus.RECOVERED);
        assertThat(infectedPerson2.getPhysicalStatus()).isEqualTo(PhysicalStatus.RECOVERED);
        assertThat(infectedPerson3.getPhysicalStatus()).isEqualTo(PhysicalStatus.HOSPITALIZED);
    }

    @Test
    void failToTreatment() throws Exception {
        //given
        PersonRequest personRequest1 = createPersonRequest("yunho1", City.SEOUL, Gender.MALE, 27, "111");
        PersonRequest personRequest2 = createPersonRequest("yunho2", City.BUSAN, Gender.MALE, 28, "222");
        PersonRequest personRequest3 = createPersonRequest("yunho3", City.INCHEON, Gender.MALE, 29, "333");
        HospitalForm hospitalForm = createHospitalForm("hos", 100);

        Long infectedPersonId1 = personService.saveInfectedPerson(personRequest1, virus.getId(), eachRecord.getId());
        Long infectedPersonId2 = personService.saveInfectedPerson(personRequest2, virus.getId(), eachRecord.getId());
        Long infectedPersonId3 = personService.saveInfectedPerson(personRequest3, virus.getId(), eachRecord.getId());
        Long hospitalId = hospitalService.makeHospital(hospitalForm);

        InfectedPerson infectedPerson1 = (InfectedPerson) personService.findPerson(infectedPersonId1).get();
        InfectedPerson infectedPerson2 = (InfectedPerson) personService.findPerson(infectedPersonId2).get();
        InfectedPerson infectedPerson3 = (InfectedPerson) personService.findPerson(infectedPersonId3).get();

        //when
        Hospital hospital = hospitalService.findHospital(hospitalId);
        hospitalService.hospitalize(hospital.getId(), infectedPersonId1, infectedPersonId2, infectedPersonId3);

        hospitalService.failToTreatment(hospital.getId(), infectedPersonId3);

        //then
        assertThat(hospital.getName()).isEqualTo("hos");
        assertThat(hospital.getNumberOfBed()).isEqualTo(98);
        assertThat(hospital.getInfectedPersonList()).extracting("name")
                .containsExactly("yunho1", "yunho2");

        assertThat(infectedPerson1.getPhysicalStatus()).isEqualTo(PhysicalStatus.HOSPITALIZED);
        assertThat(infectedPerson2.getPhysicalStatus()).isEqualTo(PhysicalStatus.HOSPITALIZED);
        assertThat(infectedPerson3.getPhysicalStatus()).isEqualTo(PhysicalStatus.DEAD);
    }

    private PersonRequest createPersonRequest(String name, City city, Gender gender, int age, String phoneNumber) {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setName(name);
        personRequest.setCity(city);
        personRequest.setGender(gender);
        personRequest.setAge(age);
        personRequest.setPhoneNumber(phoneNumber);

        return personRequest;
    }

    private HospitalForm createHospitalForm(String name, int numberOfBed) {
        HospitalForm hospitalForm = new HospitalForm();
        hospitalForm.setName(name);
        hospitalForm.setNumberOfBed(numberOfBed);

        return hospitalForm;
    }

    private Vaccine createVaccine(String name, String developer, int stockQuantity) {
        return Vaccine.createVaccine(name, developer, stockQuantity);
    }

    private Virus createVirus(VirusType virusType, String initialPoint) {
        return Virus.createVirus(virusType, initialPoint);
    }

    private TotalRecord createTotalRecord() {
        return new TotalRecordRequest().toEntity();
    }

    private EachRecordRequest createEachRecordRequest(int year, int month, int day) {
        EachRecordRequest eachRecordRequest = new EachRecordRequest();
        eachRecordRequest.setYear(year);
        eachRecordRequest.setMonth(month);
        eachRecordRequest.setDay(day);

        return eachRecordRequest;
    }

    private RouteInformationRequest createRouteInformationRequest(City city) {
        RouteInformationRequest routeInformationRequest = new RouteInformationRequest();
        routeInformationRequest.setCity(city);
        routeInformationRequest.setDistrict(null);
        routeInformationRequest.setStreet(null);
        routeInformationRequest.setEtc(null);
        routeInformationRequest.setCCTV(true);

        routeInformationRequest.setStartYear(2022);
        routeInformationRequest.setStartMonth(4);
        routeInformationRequest.setStartDay(20);
        routeInformationRequest.setStartHour(1);
        routeInformationRequest.setStartMin(10);

        routeInformationRequest.setEndYear(2022);
        routeInformationRequest.setEndMonth(4);
        routeInformationRequest.setEndDay(20);
        routeInformationRequest.setEndHour(2);
        routeInformationRequest.setEndMin(20);

        return routeInformationRequest;
    }
}
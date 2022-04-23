package coronaManagement.domain.person;

import coronaManagement.domain.person.dto.PersonRequest;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.dto.EachRecordRequest;
import coronaManagement.domain.record.dto.TotalRecordRequest;
import coronaManagement.domain.routeInformation.dto.RouteInformationRequest;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.virus.Virus;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonServiceTest {

    @Autowired PersonService personService;
    @Autowired EntityManager em;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    @Commit
    void savePerson() throws Exception {
        //given
        Vaccine vaccine = createVaccine("vaccine", "developer", 100);
        Virus virus = createVirus(VirusType.ALPHA, "China");

        EachRecordRequest eachRecordRequest = createEachRecordRequest(2022, 4, 19);
        EachRecord eachRecord = eachRecordRequest.toEntity();
        eachRecord.updateField(new TotalRecordRequest().toEntity());

        em.persist(vaccine);
        em.persist(virus);
        em.persist(eachRecord);

        PersonRequest personRequest1 = createPersonRequest("yunho1", City.SEOUL, Gender.MALE, 27, "111");
        PersonRequest personRequest2 = createPersonRequest("yunho2", City.BUSAN, Gender.MALE, 28, "222");
        PersonRequest personRequest3 = createPersonRequest("yunho3", City.INCHEON, Gender.FEMALE, 29, "333");
        PersonRequest personRequest4 = createPersonRequest("yunho4", City.DAEGU, Gender.FEMALE, 30, "444");

        Long vaccinationPersonId = personService.saveVaccinationPerson(personRequest1, vaccine.getId(), eachRecord.getId());
        Long notVaccinationPersonId = personService.saveNotVaccinationPerson(personRequest2);
        Long infectedPersonId = personService.saveInfectedPerson(personRequest3, virus.getId(), eachRecord.getId());

        VaccinationPerson vaccinationPerson = (VaccinationPerson) personService.findPerson(vaccinationPersonId).get();
        NotVaccinationPerson notVaccinationPerson = (NotVaccinationPerson) personService.findPerson(notVaccinationPersonId).get();
        InfectedPerson infectedPerson = (InfectedPerson) personService.findPerson(infectedPersonId).get();

        //when
        RouteInformationRequest routeInformationRequest = createRouteInformationRequest(City.SEOUL);
        Long contactedPersonId = personService.saveContactedPerson(personRequest4, routeInformationRequest, infectedPerson.getId());
        ContactedPerson contactedPerson = (ContactedPerson) personService.findPerson(contactedPersonId).get();

        em.flush();
        em.clear();

        //then
        assertThat(vaccinationPerson.getName()).isEqualTo("yunho1");
        assertThat(notVaccinationPerson.getName()).isEqualTo("yunho2");
        assertThat(infectedPerson.getName()).isEqualTo("yunho3");
        assertThat(contactedPerson.getName()).isEqualTo("yunho4");

        assertThat(vaccine.getStockQuantity()).isEqualTo(99);
        assertThat(virus.getInfectionCount()).isEqualTo(1);
    }

    @Test
    @Commit
    void reVaccination() throws Exception {
        //given


        //when


        //then
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

    private InfectedPerson createInfectedPerson(String name, Virus virus, EachRecord eachRecord) {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setName(name);

        return (InfectedPerson) personRequest.infectedPersonToEntity();
    }

    private Vaccine createVaccine(String name, String developer, int stockQuantity) {
        return Vaccine.createVaccine(name, developer, stockQuantity);
    }

    private Virus createVirus(VirusType virusType, String initialPoint) {
        return Virus.createVirus(virusType, initialPoint);
    }

    private TotalRecordRequest createTotalRecordRequest() {
        return new TotalRecordRequest();
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
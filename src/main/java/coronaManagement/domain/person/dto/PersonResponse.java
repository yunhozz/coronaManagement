package coronaManagement.domain.person.dto;

import coronaManagement.domain.hospital.Hospital;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.Person;
import coronaManagement.domain.person.VaccinationPerson;
import coronaManagement.domain.routeInformation.Address;
import coronaManagement.domain.routeInformation.RouteInformation;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.virus.Virus;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import coronaManagement.global.enums.PhysicalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PersonResponse {

    private String name;
    private City city;
    private Gender gender;
    private int age;
    private String phoneNumber;

    //vaccinationPerson
    private Vaccine vaccine;
    private int vaccinationCount;
    private LocalDateTime vaccinationDate;
    private InfectionStatus infectionStatus;

    //infectedPerson
    private Virus virus;
    private Hospital hospital;
    private LocalDateTime infectedTime;
    private PhysicalStatus physicalStatus;
    private String distinguishId;

    //contactedPerson
    private RouteInformation routeInformation;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public PersonResponse(Person person) {
        name = person.getName();
        city = person.getCity();
        gender = person.getGender();
        age = person.getAge();
        phoneNumber = person.getPhoneNumber();
    }

    public PersonResponse(VaccinationPerson vaccinationPerson) {
        vaccine = vaccinationPerson.getVaccine();
        vaccinationCount = vaccinationPerson.getVaccinationCount();
        vaccinationDate = vaccinationPerson.getVaccinationDate();
        infectionStatus = vaccinationPerson.getInfectionStatus();
    }

    public PersonResponse(InfectedPerson infectedPerson) {
        virus = infectedPerson.getVirus();
        hospital = infectedPerson.getHospital();
        infectedTime = infectedPerson.getInfectedTime();
        physicalStatus = infectedPerson.getPhysicalStatus();
        distinguishId = infectedPerson.getDistinguishId();
    }
}

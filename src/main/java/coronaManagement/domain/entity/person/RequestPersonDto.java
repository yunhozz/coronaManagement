package coronaManagement.domain.entity.person;

import coronaManagement.domain.entity.Address;
import coronaManagement.domain.entity.RouteInformation;
import coronaManagement.domain.entity.Vaccine;
import coronaManagement.domain.entity.Virus;
import coronaManagement.domain.enums.City;
import coronaManagement.domain.enums.Gender;
import coronaManagement.domain.enums.InfectionStatus;
import coronaManagement.domain.enums.PhysicalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestPersonDto {

    private String name;
    private City city;
    private Gender gender;
    private int age;
    private int phoneNumber;

    //infectedPerson
    private Virus virus;
    private Address infectedAddress;

    //contactedPerson
    private RouteInformation routeInformation;
    private LocalDateTime contactDate;

    //personWhoVaccination
    private Vaccine vaccine;
    private int vaccinationCount;
    private LocalDateTime vaccinationDate;

    public Person infectedPersonToEntity() {
        return InfectedPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .virus(virus)
                .infectedTime(LocalDateTime.now())
                .physicalStatus(PhysicalStatus.INFECTED)
                .build();
    }

    public Person contactedPersonToEntity() {
        return ContactedPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .routeInformation(routeInformation)
                .contactDate(contactDate)
                .infectionStatus(InfectionStatus.BEFORE_INFECT)
                .build();
    }

    public Person personWhoVaccinationToEntity() {
        return PersonWhoVaccination.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .vaccine(vaccine)
                .vaccinationCount(1)
                .vaccinationDate(LocalDateTime.now())
                .build();
    }

    public Person personBeforeVaccinationToEntity() {
        return PersonBeforeVaccination.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .build();
    }
}

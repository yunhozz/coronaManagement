package coronaManagement.domain.person;

import coronaManagement.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PersonDto {

    private String name;
    private City city;
    private Gender gender;
    private int age;
    private int phoneNumber;

    private Virus virus;
    private Address infectedAddress;

    private RouteInformation routeInformation;
    private LocalDateTime contactDate;

    private Vaccine vaccine;
    private int vaccinationCount;
    private LocalDateTime vaccinationDate;

    public InfectedPerson infectedPersonToEntity() {
        return InfectedPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .virus(virus)
                .infectedTime(LocalDateTime.now())
                .physicalStatus(PhysicalStatus.ISOLATED)
                .build();
    }

    public ContactedPerson contactedPersonToEntity() {
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

    public PersonWhoVaccination personWhoVaccinationToEntity() {
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

    public PersonBeforeVaccination personBeforeVaccinationToEntity() {
        return PersonBeforeVaccination.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .build();
    }
}

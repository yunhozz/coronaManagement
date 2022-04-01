package coronaManagement.global.dto;

import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.routeInformation.Address;
import coronaManagement.domain.routeInformation.RouteInformation;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.virus.Virus;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import coronaManagement.global.enums.PhysicalStatus;
import coronaManagement.domain.person.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PersonDto {

    @NotEmpty private String name;
    @NotEmpty private City city;
    @NotEmpty private Gender gender;
    @NotEmpty private int age;
    @NotEmpty private int phoneNumber;

    private EachRecord eachRecord;
    private String distinguishId;

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

    public Person vaccinationPersonToEntity() {
        return VaccinationPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .vaccine(vaccine)
                .eachRecord(eachRecord)
                .vaccinationCount(1)
                .vaccinationDate(LocalDateTime.now())
                .infectionStatus(InfectionStatus.BEFORE_INFECT)
                .build();
    }

    public Person notVaccinationPersonToEntity() {
        return NotVaccinationPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .infectionStatus(InfectionStatus.BEFORE_INFECT)
                .build();
    }

    public Person infectedPersonToEntity() {
        return InfectedPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .virus(virus)
                .eachRecord(eachRecord)
                .infectedTime(LocalDateTime.now())
                .physicalStatus(PhysicalStatus.INFECTED)
                .distinguishId(distinguishId)
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
}

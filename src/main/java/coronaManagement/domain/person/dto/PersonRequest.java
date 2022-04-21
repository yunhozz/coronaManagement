package coronaManagement.domain.person.dto;

import coronaManagement.domain.person.*;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import coronaManagement.global.enums.PhysicalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PersonRequest {

    private Long personId;
    @NotEmpty private String name;
    @NotEmpty private City city;
    @NotEmpty private Gender gender;
    @NotEmpty private int age;
    @NotEmpty private String phoneNumber;

    //vaccinationPerson
    private int vaccinationCount;
    private LocalDateTime vaccinationDate;

    //infectedPerson
    private String distinguishId;

    public Person vaccinationPersonToEntity() {
        return VaccinationPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
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
                .infectionStatus(InfectionStatus.BEFORE_INFECT)
                .build();
    }
}

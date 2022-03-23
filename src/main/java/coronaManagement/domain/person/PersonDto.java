package coronaManagement.domain.person;

import coronaManagement.domain.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonDto {

    private String name;
    private City city;
    private Gender gender;
    private int age;
    private int phoneNumber;

    private Virus virus;
    private Address infectedAddress;

    public InfectedPerson infectedPersonToEntity() {
        return InfectedPerson.builder()
                .name(name)
                .city(city)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .virus(virus)
                .infectedTime(LocalDateTime.now())
                .infectedAddress(infectedAddress)
                .physicalStatus(PhysicalStatus.ISOLATED)
                .build();
    }

    public PersonWhoVaccination personWhoVaccinationToEntity() {

    }
}

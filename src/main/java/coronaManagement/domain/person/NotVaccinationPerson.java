package coronaManagement.domain.person;

import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import coronaManagement.global.exception.NotAllowedPersonException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static coronaManagement.global.enums.InfectionStatus.INFECTED;

@Entity
@Getter
@DiscriminatorValue("NV")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotVaccinationPerson extends Person {

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;

    @Builder
    private NotVaccinationPerson(String name, City city, Gender gender, int age, String phoneNumber, InfectionStatus infectionStatus) {
        super(name, city, gender, age, phoneNumber);
        this.infectionStatus = infectionStatus;
    }

    public void getInfected() {
        if (infectionStatus == INFECTED) {
            throw new NotAllowedPersonException("This person is already infected.");
        }

        infectionStatus = INFECTED;
    }
}
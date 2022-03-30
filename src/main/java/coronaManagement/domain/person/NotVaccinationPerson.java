package coronaManagement.domain.person;

import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("NV")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotVaccinationPerson extends Person {

    @Builder
    private NotVaccinationPerson(String name, City city, Gender gender, int age, int phoneNumber) {
        super(name, city, gender, age, phoneNumber);
    }
}
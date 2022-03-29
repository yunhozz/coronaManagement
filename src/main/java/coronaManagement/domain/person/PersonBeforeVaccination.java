package coronaManagement.domain.person;

import coronaManagement.domain.enums.City;
import coronaManagement.domain.enums.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("BV")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonBeforeVaccination extends Person {

    @Builder
    private PersonBeforeVaccination(String name, City city, Gender gender, int age, int phoneNumber) {
        super(name, city, gender, age, phoneNumber);
    }
}
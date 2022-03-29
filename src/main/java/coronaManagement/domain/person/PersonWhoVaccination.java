package coronaManagement.domain.person;

import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.domain.vaccine.Vaccine;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("V")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonWhoVaccination extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    private int vaccinationCount;

    private LocalDateTime vaccinationDate;

    @Builder
    private PersonWhoVaccination(String name, City city, Gender gender, int age, int phoneNumber, Vaccine vaccine,
                                int vaccinationCount, LocalDateTime vaccinationDate) {

        super(name, city, gender, age, phoneNumber);
        this.vaccine = vaccine;
        this.vaccinationCount = vaccinationCount;
        this.vaccinationDate = vaccinationDate;

        vaccine.removeQuantity(1);
    }

    public void reVaccination() {
        vaccine.removeQuantity(1);
        vaccinationCount++;
        this.vaccinationDate = LocalDateTime.now();
    }
}

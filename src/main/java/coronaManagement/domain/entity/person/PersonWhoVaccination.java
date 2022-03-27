package coronaManagement.domain.entity.person;

import coronaManagement.domain.enums.City;
import coronaManagement.domain.enums.Gender;
import coronaManagement.domain.entity.Vaccine;
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
        setVaccinationDate(LocalDateTime.now());
    }

    private void setVaccinationDate(LocalDateTime vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }
}
package coronaManagement.domain.person;

import coronaManagement.domain.record.EachRecord;
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
public class VaccinationPerson extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "each_record_id")
    private EachRecord eachRecord;

    private int vaccinationCount;
    private LocalDateTime vaccinationDate;

    @Builder
    private VaccinationPerson(String name, City city, Gender gender, int age, int phoneNumber, Vaccine vaccine, EachRecord eachRecord,
                              int vaccinationCount, LocalDateTime vaccinationDate) {

        super(name, city, gender, age, phoneNumber);
        this.vaccine = vaccine;
        this.eachRecord = eachRecord;
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

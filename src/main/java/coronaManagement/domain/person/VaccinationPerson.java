package coronaManagement.domain.person;

import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import coronaManagement.global.exception.NotAllowedPersonException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static coronaManagement.global.enums.InfectionStatus.*;

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

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;

    @Builder
    private VaccinationPerson(String name, City city, Gender gender, int age, String phoneNumber, int vaccinationCount,
                              LocalDateTime vaccinationDate, InfectionStatus infectionStatus) {

        super(name, city, gender, age, phoneNumber);
        this.vaccinationCount = vaccinationCount;
        this.vaccinationDate = vaccinationDate;
        this.infectionStatus = infectionStatus;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public void setEachRecord(EachRecord eachRecord) {
        this.eachRecord = eachRecord;
    }

    public void reVaccination() {
        vaccine.removeQuantity(1);
        vaccinationCount++;
        vaccinationDate = LocalDateTime.now();
    }

    public void getInfected() {
        if (infectionStatus == INFECTED) {
            throw new NotAllowedPersonException("This person is already infected.");
        }

        infectionStatus = INFECTED;
    }
}

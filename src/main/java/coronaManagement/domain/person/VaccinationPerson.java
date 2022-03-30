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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("V")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VaccinationPerson extends Person {

    @OneToMany(mappedBy = "vaccination_person")
    private List<VaccinationPersonToInfected> vaccinationPersonToInfectedList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "each_record_id")
    private EachRecord eachRecord;

    private int vaccinationCount;
    private LocalDateTime vaccinationDate;

    @Builder
    private VaccinationPerson(String name, City city, Gender gender, int age, int phoneNumber, Vaccine vaccine, EachRecord eachRecord, int vaccinationCount,
                              LocalDateTime vaccinationDate, VaccinationPersonToInfected... vaccinationPersonToInfecteds) {

        super(name, city, gender, age, phoneNumber);
        this.vaccine = vaccine;
        this.eachRecord = eachRecord;
        this.vaccinationCount = vaccinationCount;
        this.vaccinationDate = vaccinationDate;

        vaccine.removeQuantity(1);
        eachRecord.addVaccination();

        for (VaccinationPersonToInfected vaccinationPersonToInfected : vaccinationPersonToInfecteds) {
            setVaccinationPersonToInfectedList(vaccinationPersonToInfected);
        }
    }

    public void getInfected() {

    }

    public void reVaccination() {
        vaccine.removeQuantity(1);
        vaccinationCount++;
        this.vaccinationDate = LocalDateTime.now();
    }

    //연관관계 편의 메소드
    private void setVaccinationPersonToInfectedList(VaccinationPersonToInfected vaccinationPersonToInfected) {
        vaccinationPersonToInfectedList.add(vaccinationPersonToInfected);
        vaccinationPersonToInfected.updateVaccinationPerson(this);
    }
}

package coronaManagement.domain.person;

import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("NV")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotVaccinationPerson extends Person {

    @OneToMany(mappedBy = "not_vaccination_person")
    private List<NotVaccinationPersonToInfected> notVaccinationPersonToInfectedList = new ArrayList<>();

    @Builder
    private NotVaccinationPerson(String name, City city, Gender gender, int age, int phoneNumber) {
        super(name, city, gender, age, phoneNumber);
    }

    public void getInfected() {

    }

    //연관관계 편의 메소드
    private void setNotVaccinationPersonToInfectedList(NotVaccinationPersonToInfected notVaccinationPersonToInfected) {
        notVaccinationPersonToInfectedList.add(notVaccinationPersonToInfected);
        notVaccinationPersonToInfected.updateNotVaccinationPerson(this);
    }
}
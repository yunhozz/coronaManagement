package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
import coronaManagement.domain.Nation;
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

    private int vaccinationCount;

    private LocalDateTime vaccinationDate;

    @Builder
    private PersonWhoVaccination(Nation nation, BasicInfo basicInfo, int vaccinationCount, LocalDateTime vaccinationDate) {
        super(nation, basicInfo);
        this.vaccinationCount = vaccinationCount;
        this.vaccinationDate = vaccinationDate;
    }
}

package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
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
}

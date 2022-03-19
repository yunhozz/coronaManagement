package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonWhoVaccination extends Person {

    @Id
    @GeneratedValue
    @Column(name = "person_who_vaccination_id")
    private Long id;

    private int vaccinationCount;

    private LocalDateTime vaccinationDate;
}

package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
import coronaManagement.domain.InfectionStatus;
import coronaManagement.domain.Nation;
import coronaManagement.domain.person.InfectedPerson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactedPerson extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infected_person_id")
    private InfectedPerson infectedPerson;

    @Embedded
    private BasicInfo basicInfo;

    private LocalDateTime contactTime;

    private String contactPlace;

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;

    public ContactedPerson(Nation nation, BasicInfo basicInfo, InfectedPerson infectedPerson, BasicInfo basicInfo1,
                           LocalDateTime contactTime, String contactPlace, InfectionStatus infectionStatus) {

        super(nation, basicInfo);
        this.infectedPerson = infectedPerson;
        this.basicInfo = basicInfo1;
        this.contactTime = contactTime;
        this.contactPlace = contactPlace;
        this.infectionStatus = infectionStatus;
    }
}
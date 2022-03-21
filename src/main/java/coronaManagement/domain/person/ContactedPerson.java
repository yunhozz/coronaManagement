package coronaManagement.domain.person;

import coronaManagement.domain.InfectionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("C")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactedPerson extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infected_person_id")
    private InfectedPerson infectedPerson;

    private LocalDateTime contactTime;

    private String contactPlace;

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;
}

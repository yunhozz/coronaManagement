package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactedPerson {

    @Id
    @GeneratedValue
    @Column(name = "contacted_person_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infected_person_id")
    private InfectedPerson infectedPerson;

    @Embedded
    private BasicInfo basicInfo;

    private LocalDateTime contactTime;

    private String contactPlace;

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;
}

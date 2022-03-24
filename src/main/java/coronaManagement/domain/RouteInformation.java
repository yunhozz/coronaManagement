package coronaManagement.domain;

import coronaManagement.domain.person.InfectedPerson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RouteInformation {

    @Id
    @GeneratedValue
    @Column(name = "route_information_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infected_person_id")
    private InfectedPerson infectedPerson;

    @Embedded
    private Address address;

    private boolean isCCTV;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    private RouteInformation(InfectedPerson infectedPerson, Address address, boolean isCCTV, LocalDateTime startTime, LocalDateTime endTime) {
        this.infectedPerson = infectedPerson;
        this.address = address;
        this.isCCTV = isCCTV;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

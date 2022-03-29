package coronaManagement.domain.person;

import coronaManagement.domain.enums.City;
import coronaManagement.domain.enums.Gender;
import coronaManagement.domain.enums.InfectionStatus;
import coronaManagement.domain.routeInformation.RouteInformation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("C")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactedPerson extends Person {

    @Id
    @GeneratedValue
    @Column(name = "contacted_person_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_information_id")
    private RouteInformation routeInformation;

    private LocalDateTime contactDate;

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;

    @Builder
    private ContactedPerson(String name, City city, Gender gender, int age, int phoneNumber, RouteInformation routeInformation,
                           LocalDateTime contactDate, InfectionStatus infectionStatus) {

        super(name, city, gender, age, phoneNumber);
        this.routeInformation = routeInformation;
        this.contactDate = contactDate;
        this.infectionStatus = infectionStatus;
    }

    public void getInfected() {
        if (infectionStatus == InfectionStatus.INFECTED) {
            throw new IllegalStateException("This person is already infected.");
        }

        setInfectionStatus(InfectionStatus.INFECTED);
    }

    private void setInfectionStatus(InfectionStatus infectionStatus) {
        this.infectionStatus = infectionStatus;
    }
}

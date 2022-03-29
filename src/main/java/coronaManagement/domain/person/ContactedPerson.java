package coronaManagement.domain.person;

import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.InfectionStatus;
import coronaManagement.domain.routeInformation.RouteInformation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static coronaManagement.global.enums.InfectionStatus.*;

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
    private InfectionStatus infectionStatus; //BEFORE_INFECT, INFECTED

    @Builder
    private ContactedPerson(String name, City city, Gender gender, int age, int phoneNumber, RouteInformation routeInformation,
                           LocalDateTime contactDate, InfectionStatus infectionStatus) {

        super(name, city, gender, age, phoneNumber);
        this.routeInformation = routeInformation;
        this.contactDate = contactDate;
        this.infectionStatus = infectionStatus;
    }

    public void getInfected() {
        if (infectionStatus == INFECTED) {
            throw new IllegalStateException("This person is already infected.");
        }

        this.infectionStatus = INFECTED;
    }
}

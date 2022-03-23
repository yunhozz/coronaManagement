package coronaManagement.domain.person;

import coronaManagement.domain.City;
import coronaManagement.domain.Gender;
import coronaManagement.domain.InfectionStatus;
import coronaManagement.domain.RouteInformation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;

    @Builder
    private ContactedPerson(String name, City city, Gender gender, int age, int phoneNumber, RouteInformation routeInformation,
                           InfectionStatus infectionStatus) {

        super(name, city, gender, age, phoneNumber);
        this.routeInformation = routeInformation;
        this.infectionStatus = infectionStatus;
    }
}

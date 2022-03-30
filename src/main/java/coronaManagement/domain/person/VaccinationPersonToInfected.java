package coronaManagement.domain.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VaccinationPersonToInfected {

    @Id
    @GeneratedValue
    @Column(name = "vaccination_person_to_infected")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccination_person_id")
    private VaccinationPerson vaccinationPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infected_person_id")
    private InfectedPerson infectedPerson;

    public VaccinationPersonToInfected(VaccinationPerson vaccinationPerson, InfectedPerson infectedPerson) {
        this.vaccinationPerson = vaccinationPerson;
        this.infectedPerson = infectedPerson;
    }
}

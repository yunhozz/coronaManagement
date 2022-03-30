package coronaManagement.domain.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotVaccinationPersonToInfected {

    @Id
    @GeneratedValue
    @Column(name = "not_vaccination_person_to_infected")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "not_vaccination_person_id")
    private NotVaccinationPerson notVaccinationPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infected_person_id")
    private InfectedPerson infectedPerson;

    public NotVaccinationPersonToInfected(NotVaccinationPerson notVaccinationPerson, InfectedPerson infectedPerson) {
        this.notVaccinationPerson = notVaccinationPerson;
        this.infectedPerson = infectedPerson;
    }

    public void getInfected() {

    }

    public void updateNotVaccinationPerson(NotVaccinationPerson notVaccinationPerson) {
        this.notVaccinationPerson = notVaccinationPerson;
    }
}

package coronaManagement.domain;

import coronaManagement.domain.person.InfectedPerson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Virus {

    @Id
    @GeneratedValue
    @Column(name = "virus_id")
    private Long id;

    @OneToOne(mappedBy = "virus")
    private InfectedPerson infectedPerson;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String initialConfirm;

    private double infectionRate;

    private double fatalityRate;



    public double calculateInfectionRate() {
        double population = infectedPerson.getNation().getPopulation();
        double numOfInfection = infectedPerson.getNation().getNumOfInfection();

        return numOfInfection / population * 100;
    }

    public void updateInfectedPerson(InfectedPerson infectedPerson) {
        this.infectedPerson = infectedPerson;
    }
}

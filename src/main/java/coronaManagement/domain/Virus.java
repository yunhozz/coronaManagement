package coronaManagement.domain;

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
        int population = infectedPerson.getNation().getPopulation();
        int numOfInfection = infectedPerson.getNation().getNumOfInfection();

        return (double) numOfInfection / (double) population * 100;
    }

    protected void setInfectedPerson(InfectedPerson infectedPerson) {
        this.infectedPerson = infectedPerson;
    }
}

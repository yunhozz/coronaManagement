package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nation {

    @Id
    @GeneratedValue
    @Column(name = "city_id")
    private Long id;

    private String name;

    private int population;

    private int numOfVaccination;

    private int numOfInfection;

    public void addPopulation(int population) {
        this.population += population;
    }

    public void removePopulation(int population) {
        this.population -= population;
    }

    public void addNumOfVaccination(int number) {
        numOfVaccination += number;
    }

    public void addNumOfInfection(int number) {
        numOfInfection += number;
    }

    public void removeNumOfInfection(int number) {
        numOfInfection -= number;
    }

    public double getVaccinationRate() {
        return (double) numOfVaccination / population * 100;
    }
}

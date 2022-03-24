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
public class TotalRecord {

    @Id
    @GeneratedValue
    @Column(name = "total_record_id")
    private Long id;

    private int totalInfection;

    private int totalDeath;

    private int totalVaccination;

    public TotalRecord(int totalInfection, int totalDeath, int totalVaccination) {
        this.totalInfection = totalInfection;
        this.totalDeath = totalDeath;
        this.totalVaccination = totalVaccination;
    }

    public void addInfection(int eachInfection) {
        totalInfection += eachInfection;
    }

    public void addDeath(int eachDeath) {
        totalDeath += eachDeath;
    }

    public void addVaccination(int eachVaccination) {
        totalVaccination += eachVaccination;
    }
}

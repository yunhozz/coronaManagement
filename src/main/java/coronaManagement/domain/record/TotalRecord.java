package coronaManagement.domain.record;

import coronaManagement.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalRecord extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "total_record_id")
    private Long id;

    private int totalInfection;
    private int totalDeath;
    private int totalVaccination;

    @Builder
    private TotalRecord(int totalInfection, int totalDeath, int totalVaccination) {
        this.totalInfection = totalInfection;
        this.totalDeath = totalDeath;
        this.totalVaccination = totalVaccination;
    }

    public void addInfection() {
        totalInfection += 1;
    }

    public void addInfection(int totalInfection) {
        this.totalInfection += totalInfection;
    }

    public void addDeath() {
        totalDeath += 1;
    }

    public void addDeath(int totalDeath) {
        this.totalDeath += totalDeath;
    }

    public void addVaccination() {
        totalVaccination += 1;
    }

    public void addVaccination(int totalVaccination) {
        this.totalVaccination += totalVaccination;
    }

    public double getFatalityRate() {
        return (double) totalDeath / totalInfection * 100;
    }

    public double getVaccinationRate(int population) {
        return (double) totalVaccination / population * 100;
    }
}

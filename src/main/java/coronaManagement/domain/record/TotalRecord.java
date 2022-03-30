package coronaManagement.domain.record;

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

    public void addInfection() {
        this.totalInfection += 1;
    }

    public void addDeath() {
        this.totalDeath += 1;
    }

    public void addVaccination() {
        this.totalVaccination += 1;
    }
}

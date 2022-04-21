package coronaManagement.domain.record;

import coronaManagement.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EachRecord extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "each_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "total_record_id")
    private TotalRecord totalRecord;

    private LocalDate recordDate;
    private int todayInfection;
    private int todayDeath;
    private int todayVaccination;

    @Builder
    private EachRecord(LocalDate recordDate, int todayInfection, int todayDeath, int todayVaccination) {
        this.recordDate = recordDate;
        this.todayInfection = todayInfection;
        this.todayDeath = todayDeath;
        this.todayVaccination = todayVaccination;
    }

    public void updateField(TotalRecord totalRecord) {
        this.totalRecord = totalRecord;
    }

    public void addInfection() {
        todayInfection += 1;
        totalRecord.addInfection();
    }

    public void addDeath() {
        todayDeath += 1;
        totalRecord.addDeath();
    }

    public void addVaccination() {
        todayVaccination += 1;
        totalRecord.addVaccination();
    }

    public double getFatalityRateOfToday() {
        return (double) todayDeath / todayInfection * 100;
    }
}

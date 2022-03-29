package coronaManagement.domain.record;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EachRecord {

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
    private EachRecord(TotalRecord totalRecord, LocalDate recordDate, int todayInfection, int todayDeath, int todayVaccination) {
        this.totalRecord = totalRecord;
        this.recordDate = recordDate;
        this.todayInfection = todayInfection;
        this.todayDeath = todayDeath;
        this.todayVaccination = todayVaccination;
    }

    public void addInfection(int todayInfection) {
        this.todayInfection += todayInfection;
        totalRecord.addInfection(todayInfection);
    }

    public void addDeath(int todayDeath) {
        this.todayDeath += todayDeath;
        totalRecord.addDeath(todayDeath);
    }

    public void addVaccination(int todayVaccination) {
        this.todayVaccination += todayVaccination;
        totalRecord.addVaccination(todayVaccination);
    }
}

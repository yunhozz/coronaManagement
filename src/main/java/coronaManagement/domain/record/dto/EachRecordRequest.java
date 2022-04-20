package coronaManagement.domain.record.dto;

import coronaManagement.domain.record.EachRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EachRecordRequest {

    private int year;
    private int month;
    private int day;
    private int todayInfection;
    private int todayDeath;
    private int todayVaccination;

    public EachRecord toEntity() {
        return EachRecord.builder()
                .recordDate(LocalDate.of(year, month, day))
                .todayInfection(0)
                .todayDeath(0)
                .todayVaccination(0)
                .build();
    }
}

package coronaManagement.global.dto.request;

import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.TotalRecord;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EachRecordRequestDto {

    private TotalRecord totalRecord;
    private int year;
    private int month;
    private int day;
    private int todayInfection;
    private int todayDeath;
    private int todayVaccination;

    public EachRecord toEntity() {
        return EachRecord.builder()
                .totalRecord(totalRecord)
                .recordDate(LocalDate.of(year, month, day))
                .todayInfection(0)
                .todayDeath(0)
                .todayVaccination(0)
                .build();
    }
}

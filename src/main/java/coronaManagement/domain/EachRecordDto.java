package coronaManagement.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EachRecordDto {

    private TotalRecord totalRecord;
    private int year;
    private int month;
    private int day;

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

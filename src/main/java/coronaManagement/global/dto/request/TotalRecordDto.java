package coronaManagement.global.dto.request;

import coronaManagement.domain.record.TotalRecord;
import lombok.Data;

@Data
public class TotalRecordDto {

    private int totalInfection;
    private int totalDeath;
    private int totalVaccination;

    public TotalRecord toEntity() {
        return TotalRecord.builder()
                .totalInfection(0)
                .totalDeath(0)
                .totalVaccination(0)
                .build();
    }
}

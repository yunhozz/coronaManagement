package coronaManagement.domain.record.dto;

import coronaManagement.domain.record.TotalRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TotalRecordRequest {

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

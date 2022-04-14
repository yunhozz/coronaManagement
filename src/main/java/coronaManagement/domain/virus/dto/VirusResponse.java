package coronaManagement.domain.virus.dto;

import coronaManagement.domain.virus.Virus;
import coronaManagement.global.enums.VirusType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VirusResponse {

    private VirusType virusType;
    private String initialPoint;
    private int infectionCount;
    private int fatalCount;

    public VirusResponse(Virus virus) {
        virusType = virus.getVirusType();
        initialPoint = virus.getInitialPoint();
        infectionCount = virus.getInfectionCount();
        fatalCount = virus.getFatalCount();
    }
}

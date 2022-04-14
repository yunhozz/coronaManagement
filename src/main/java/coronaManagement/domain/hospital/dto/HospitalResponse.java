package coronaManagement.domain.hospital.dto;

import coronaManagement.domain.hospital.Hospital;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HospitalResponse {

    private String name;
    private int numberOfBed;

    public HospitalResponse(Hospital hospital) {
        name = hospital.getName();
        numberOfBed = hospital.getNumberOfBed();
    }
}

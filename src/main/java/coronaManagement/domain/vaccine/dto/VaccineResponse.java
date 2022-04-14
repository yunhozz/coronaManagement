package coronaManagement.domain.vaccine.dto;

import coronaManagement.domain.vaccine.Vaccine;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VaccineResponse {

    private String name;
    private String developer;
    private int stockQuantity;

    public VaccineResponse(Vaccine vaccine) {
        name = vaccine.getName();
        developer = vaccine.getDeveloper();
        stockQuantity = vaccine.getStockQuantity();
    }
}

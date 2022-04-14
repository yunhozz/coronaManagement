package coronaManagement.domain.routeInformation.dto;

import coronaManagement.global.enums.City;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.routeInformation.Address;
import coronaManagement.domain.routeInformation.RouteInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RouteInformationRequest {

    private InfectedPerson infectedPerson;

    //address
    private City city;
    private String district;
    private String street;
    private String etc;

    private boolean isCCTV;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public RouteInformation toEntity() {
        return RouteInformation.builder()
                .infectedPerson(infectedPerson)
                .address(new Address(city, district, street, etc))
                .isCCTV(isCCTV)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}

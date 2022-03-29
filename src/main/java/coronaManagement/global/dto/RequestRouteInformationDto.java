package coronaManagement.global.dto;

import coronaManagement.domain.enums.City;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.routeInformation.Address;
import coronaManagement.domain.routeInformation.RouteInformation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRouteInformationDto {

    private InfectedPerson infectedPerson;
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

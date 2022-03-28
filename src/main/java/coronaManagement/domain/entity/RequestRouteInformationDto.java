package coronaManagement.domain.entity;

import coronaManagement.domain.enums.City;
import coronaManagement.domain.entity.person.InfectedPerson;
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

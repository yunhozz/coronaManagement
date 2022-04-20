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

    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startMin;

    private int endYear;
    private int endMonth;
    private int endDay;
    private int endHour;
    private int endMin;

    public RouteInformation toEntity() {
        return RouteInformation.builder()
                .infectedPerson(infectedPerson)
                .address(new Address(city, district, street, etc))
                .isCCTV(isCCTV)
                .startTime(LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin))
                .endTime(LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin))
                .build();
    }
}

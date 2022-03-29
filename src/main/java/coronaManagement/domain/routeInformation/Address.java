package coronaManagement.domain.routeInformation;

import coronaManagement.domain.enums.City;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private City city;
    private String district;
    private String street;
    private String etc;

    public Address(City city, String district, String street, String etc) {
        this.city = city;
        this.district = district;
        this.street = street;
        this.etc = etc;
    }
}

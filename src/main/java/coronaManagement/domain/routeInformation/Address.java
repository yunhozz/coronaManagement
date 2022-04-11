package coronaManagement.domain.routeInformation;

import coronaManagement.domain.BaseEntity;
import coronaManagement.global.enums.City;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {

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

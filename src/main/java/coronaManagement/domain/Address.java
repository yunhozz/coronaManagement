package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Enumerated(EnumType.STRING)
    private City city;

    private String district;
    private String street;
    private String etc;

    @Builder
    private Address(City city, String district, String street, String etc) {
        this.city = city;
        this.district = district;
        this.street = street;
        this.etc = etc;
    }
}

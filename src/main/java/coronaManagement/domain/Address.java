package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String street;
    private String etc;
    private String zipcode;

    public Address(String street, String etc, String zipcode) {
        this.street = street;
        this.etc = etc;
        this.zipcode = zipcode;
    }
}

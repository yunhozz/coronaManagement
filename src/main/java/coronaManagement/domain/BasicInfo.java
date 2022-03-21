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
public class BasicInfo {

    private String name;

    private Gender gender;

    private int age;

    @Enumerated(EnumType.STRING)
    private City city;

    private int phoneNumber;

    @Builder
    private BasicInfo(String name, Gender gender, int age, City city, int phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }
}

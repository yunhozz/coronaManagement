package coronaManagement.domain.person;

import coronaManagement.domain.City;
import coronaManagement.domain.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Person {

    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private City city;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    private int phoneNumber;

    public Person(String name, City city, Gender gender, int age, int phoneNumber) {
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }
}

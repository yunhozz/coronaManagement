package coronaManagement.global.dto.response;

import coronaManagement.domain.person.Person;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonResponseDto {

    private String name;
    private City city;
    private Gender gender;
    private int age;
    private String phoneNumber;

    public PersonResponseDto(Person person) {
        name = person.getName();
        city = person.getCity();
        gender = person.getGender();
        age = person.getAge();
        phoneNumber = person.getPhoneNumber();
    }
}

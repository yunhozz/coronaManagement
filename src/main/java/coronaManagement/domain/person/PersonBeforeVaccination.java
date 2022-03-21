package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("BV")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonBeforeVaccination extends Person {

}
package coronaManagement.domain.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("BV")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonBeforeVaccination extends Person {


}

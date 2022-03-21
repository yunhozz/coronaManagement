package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
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

    @Embedded
    private BasicInfo basicInfo;
}

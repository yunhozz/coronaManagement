package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person {

    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private Long id;

    @Embedded
    private BasicInfo basicInfo;
}

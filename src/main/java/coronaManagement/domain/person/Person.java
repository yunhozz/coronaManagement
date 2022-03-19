package coronaManagement.domain.person;

import coronaManagement.domain.BasicInfo;
import coronaManagement.domain.Nation;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @Embedded
    private BasicInfo basicInfo;
}

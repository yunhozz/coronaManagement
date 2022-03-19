package coronaManagement.domain;

import coronaManagement.domain.person.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfectedPerson extends Person {

    @Id
    @GeneratedValue
    @Column(name = "person_who_infected_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virus_id")
    private Virus virus;

    @Embedded
    private BasicInfo basicInfo;

    @Embedded
    private Address address;

    private int phoneNumber;

    private String email;

    private LocalDateTime infectedTime;

    private String infectedPlace;

    @Enumerated(EnumType.STRING)
    private PhysicalStatus physicalStatus;

    /**
     * 연관관계 편의 메소드
     */
    public void setVirus(Virus virus) {
        this.virus = virus;
        virus.setInfectedPerson(this);
    }


}

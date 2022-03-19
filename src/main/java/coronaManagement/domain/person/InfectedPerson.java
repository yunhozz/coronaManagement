package coronaManagement.domain.person;

import coronaManagement.domain.*;
import coronaManagement.domain.person.Person;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("I")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfectedPerson extends Person {

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
    private void setVirus(Virus virus) {
        this.virus = virus;
        virus.updateInfectedPerson(this);
    }

    @Builder
    private InfectedPerson(Nation nation, BasicInfo basicInfo, Virus virus, BasicInfo basicInfo1, Address address,int phoneNumber, String email,
                          LocalDateTime infectedTime, String infectedPlace, PhysicalStatus physicalStatus) {

        super(nation, basicInfo);
        setVirus(virus);
        this.basicInfo = basicInfo1;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.infectedTime = infectedTime;
        this.infectedPlace = infectedPlace;
        this.physicalStatus = physicalStatus;
    }
}

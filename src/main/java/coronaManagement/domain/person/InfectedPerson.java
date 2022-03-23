package coronaManagement.domain.person;

import coronaManagement.domain.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virus_id")
    private Virus virus;

    private LocalDateTime infectedTime;

    @Embedded
    private Address infectedAddress;

    @Enumerated(EnumType.STRING)
    private PhysicalStatus physicalStatus;

    @Builder
    private InfectedPerson(String name, City city, Gender gender, int age, int phoneNumber, Virus virus,
                           LocalDateTime infectedTime, Address infectedAddress) {

        super(name, city, gender, age, phoneNumber);
        this.virus = virus;
        this.infectedTime = infectedTime;
        this.infectedAddress = infectedAddress;
        setPhysicalStatus(PhysicalStatus.ISOLATED);
    }

    public void getRecovered() {
        setPhysicalStatus(PhysicalStatus.RECOVERED);
    }

    public void getPassedAway() {
        setPhysicalStatus(PhysicalStatus.DEAD);
    }

    private void setPhysicalStatus(PhysicalStatus physicalStatus) {
        this.physicalStatus = physicalStatus;
    }
}

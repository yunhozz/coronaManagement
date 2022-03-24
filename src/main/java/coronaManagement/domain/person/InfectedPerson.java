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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    private LocalDateTime infectedTime;

    @Embedded
    private Address infectedAddress;

    @Enumerated(EnumType.STRING)
    private PhysicalStatus physicalStatus;

    @Builder
    private InfectedPerson(String name, City city, Gender gender, int age, int phoneNumber, Virus virus, Hospital hospital,
                          LocalDateTime infectedTime, Address infectedAddress, PhysicalStatus physicalStatus) {

        super(name, city, gender, age, phoneNumber);
        this.virus = virus;
        this.hospital = hospital;
        this.infectedTime = infectedTime;
        this.infectedAddress = infectedAddress;
        this.physicalStatus = physicalStatus;
    }

    public void getRecovered() {
        if (physicalStatus == PhysicalStatus.ISOLATED) {
            setPhysicalStatus(PhysicalStatus.RECOVERED);

        } else {
            throw new IllegalStateException("This people is already recovered or dead.");
        }
    }

    public void getPassedAway() {
        if (physicalStatus == PhysicalStatus.DEAD) {
            throw new IllegalStateException("This people is already dead.");
        }

        setPhysicalStatus(PhysicalStatus.DEAD);
        virus.addFatalCount();
    }

    private void setPhysicalStatus(PhysicalStatus physicalStatus) {
        this.physicalStatus = physicalStatus;
    }
}

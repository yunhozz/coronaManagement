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

    @Embedded
    private Address address;

    private LocalDateTime infectedTime;

    private String infectedPlace;

    @Enumerated(EnumType.STRING)
    private PhysicalStatus physicalStatus;

    /**
     * 연관관계 편의 메소드
     */
    private void setVirus(Virus virus) {
        this.virus = virus;
        virus.getInfectedPersonList().add(this);
    }
}

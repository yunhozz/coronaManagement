package coronaManagement.domain.person;

import coronaManagement.domain.hospital.Hospital;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.virus.Virus;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.PhysicalStatus;
import coronaManagement.global.exception.NotAllowedPersonException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static coronaManagement.global.enums.PhysicalStatus.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "each_record_id")
    private EachRecord eachRecord;

    private LocalDateTime infectedTime;

    @Enumerated(EnumType.STRING)
    private PhysicalStatus physicalStatus; //INFECTED, RECOVERED, ISOLATED, DEAD

    @Builder
    private InfectedPerson(String name, City city, Gender gender, int age, int phoneNumber, Virus virus, EachRecord eachRecord,
                           LocalDateTime infectedTime, PhysicalStatus physicalStatus) {

        super(name, city, gender, age, phoneNumber);
        this.virus = virus;
        this.eachRecord = eachRecord;
        this.infectedTime = infectedTime;
        this.physicalStatus = physicalStatus;

        virus.addInfectionCount();
        eachRecord.addInfection();
    }

    public InfectedPerson(String name, City city, Gender gender, int age, int phoneNumber, Virus virus, LocalDateTime infectedTime,
                          PhysicalStatus physicalStatus) {

        super(name, city, gender, age, phoneNumber);
        this.virus = virus;
        this.infectedTime = infectedTime;
        this.physicalStatus = physicalStatus;
    }

    //입원 -> Hospital 과의 연관관계 생성
    public void beHospitalized(Hospital hospital) {
        if (physicalStatus == INFECTED) {
            setHospital(hospital);
            this.physicalStatus = ISOLATED;

        } else {
            throw new NotAllowedPersonException("You can't be hospitalized.");
        }
    }

    public void recovered() {
        if (physicalStatus == ISOLATED) {
            this.physicalStatus = RECOVERED;

        } else {
            throw new NotAllowedPersonException("This people is already recovered or dead.");
        }
    }

    public void passedAway() {
        if (physicalStatus == DEAD) {
            throw new NotAllowedPersonException("This people is already dead.");
        }

        this.physicalStatus = DEAD;
        virus.addFatalCount();
        eachRecord.addDeath();
    }

    //연관관계 편의 메소드
    private void setHospital(Hospital hospital) {
        this.hospital = hospital;
        hospital.getInfectedPersonList().add(this);
    }
}

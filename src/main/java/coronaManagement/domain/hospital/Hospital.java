package coronaManagement.domain.hospital;

import coronaManagement.domain.BaseEntity;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.global.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static coronaManagement.global.enums.PhysicalStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "hospital_id")
    private Long id;

    @OneToMany(mappedBy = "hospital")
    private List<InfectedPerson> infectedPersonList = new ArrayList<>();

    private String name;
    private int numberOfBed;

    public Hospital(String name, int numberOfBed) {
        this.name = name;
        this.numberOfBed = numberOfBed;
    }

    //입원 처리
    public void hospitalize(List<InfectedPerson> infectedPersonList) {
        for (InfectedPerson infectedPerson : infectedPersonList) {
            if (infectedPerson.getHospital() == null && infectedPerson.getPhysicalStatus() == INFECTED) {
                infectedPerson.beHospitalized(this);
                removeNumberOfBed(1);
            }
        }
    }

    //치료 완료
    public void completeTreatment(List<InfectedPerson> infectedPersonList) {
        for (InfectedPerson infectedPerson : infectedPersonList) {
            infectedPerson.recovered();
            this.infectedPersonList.remove(infectedPerson);
        }

        addNumberOfBed(infectedPersonList.size());
    }

    //치료 실패
    public void failTreatment(List<InfectedPerson> infectedPersonList) {
        for (InfectedPerson infectedPerson : infectedPersonList) {
            infectedPerson.passedAway();
            this.infectedPersonList.remove(infectedPerson);
        }

        addNumberOfBed(infectedPersonList.size());
    }

    public void addNumberOfBed(int numberOfBed) {
        this.numberOfBed += numberOfBed;
    }

    public void removeNumberOfBed(int numberOfBed) {
        if (this.numberOfBed - numberOfBed < 0) {
            throw new NotEnoughStockException("Not enough number of bed.");
        }

        this.numberOfBed -= numberOfBed;
    }
}

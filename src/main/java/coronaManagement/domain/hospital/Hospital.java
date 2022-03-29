package coronaManagement.domain.hospital;

import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.global.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {

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
    public void hospitalize(InfectedPerson... infectedPeople) {
        for (InfectedPerson infectedPerson : infectedPeople) {
            if (infectedPerson.getHospital() == null) {
                infectedPerson.beHospitalized(this);
            }
        }

        removeNumberOfBed(infectedPeople.length);
    }

    //치료 완료
    public void completeTreatment(InfectedPerson... infectedPeople) {
        for (InfectedPerson infectedPerson : infectedPeople) {
            infectedPerson.recovered();
        }

        addNumberOfBed(infectedPeople.length);
    }

    //치료 실패
    public void failTreatment(InfectedPerson... infectedPeople) {
        for (InfectedPerson infectedPerson : infectedPeople) {
            infectedPerson.passedAway();
        }

        addNumberOfBed(infectedPeople.length);
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

package coronaManagement.domain;

import coronaManagement.domain.person.InfectedPerson;
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

    public void completeTreatment(InfectedPerson... infectedPeople) {
        for (InfectedPerson infectedPerson : infectedPeople) {
            infectedPerson.recovered();
        }

        addNumberOfBed(infectedPeople.length);
    }

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
        int remainBed = this.numberOfBed - numberOfBed;

        if (remainBed < 0) {
            throw new IllegalStateException("Not enough number of bed.");
        }

        this.numberOfBed = remainBed;
    }
}

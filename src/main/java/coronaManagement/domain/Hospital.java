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

    public void treatPatient() {

    }

    public void addNumberOfBed() {

    }

    public void removeNumberOfBed() {

    }
}

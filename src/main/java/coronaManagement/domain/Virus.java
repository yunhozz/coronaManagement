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
public class Virus {

    @Id
    @GeneratedValue
    @Column(name = "virus_id")
    private Long id;

    @OneToMany(mappedBy = "virus")
    private List<InfectedPerson> infectedPersonList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Type type;

    private String initialConfirm;

    private double infectionRate;

    private double fatalityRate;

    public double calculateInfectionRate() {

    }

    public double calculateFatalityRate() {

    }
}

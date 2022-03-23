package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Virus {

    @Id
    @GeneratedValue
    @Column(name = "virus_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private VirusType virusType;

    private String initialPoint;

    private int infectionCount;

    private int fatalCount;

    private Virus(VirusType virusType, String initialPoint, int infectionCount, int fatalCount) {
        this.virusType = virusType;
        this.initialPoint = initialPoint;
        this.infectionCount = infectionCount;
        this.fatalCount = fatalCount;
    }

    public static Virus createVirus(VirusType virusType, String initialPoint) {
        return new Virus(virusType, initialPoint, 0, 0);
    }

    public void addInfectionCount() {
        infectionCount++;
    }

    public void addFatalCount() {
        fatalCount++;
    }
}

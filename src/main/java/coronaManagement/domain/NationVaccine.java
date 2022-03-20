package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NationVaccine {

    @Id
    @GeneratedValue
    @Column(name = "nation_vaccine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    /**
     * 연관관계 편의 메소드
     */
    private void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
        vaccine.getNationVaccines().add(this);
    }
}

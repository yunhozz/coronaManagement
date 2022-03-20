package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vaccine {

    @Id
    @GeneratedValue
    @Column(name = "vaccine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    private String name;

    private int stockQuantity;

    public void addQuantity(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    public void removeQuantity(int stockQuantity) {
        this.stockQuantity -= stockQuantity;
    }

    //치명률 : (감염자 수) / (백신 접종자 수) * 100
    public double calculateFatalityRate() {
        return nation.getNumOfVaccination();
    }
}

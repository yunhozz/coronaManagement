package coronaManagement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vaccine {

    @Id
    @GeneratedValue
    @Column(name = "vaccine_id")
    private Long id;

    @OneToMany(mappedBy = "vaccine")
    private List<NationVaccine> nationVaccines = new ArrayList<>();

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
        double rate = 0;

        for (NationVaccine nationVaccine : nationVaccines) {
            rate += (double) nationVaccine.getNation().getNumOfInfection() / nationVaccine.getNation().getNumOfVaccination() * 100;
        }

        return rate / nationVaccines.size();
    }
}

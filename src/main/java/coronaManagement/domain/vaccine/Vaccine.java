package coronaManagement.domain.vaccine;

import coronaManagement.domain.BaseEntity;
import coronaManagement.global.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vaccine extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "vaccine_id")
    private Long id;

    private String name;
    private String developer;
    private int stockQuantity;

    public Vaccine(String name, String developer, int stockQuantity) {
        this.name = name;
        this.developer = developer;
        this.stockQuantity = stockQuantity;
    }

    public static Vaccine createVaccine(String name, String developer, int stockQuantity) {
        return new Vaccine(name, developer, stockQuantity);
    }

    public void addQuantity(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    public void removeQuantity(int stockQuantity) {
        int remainQuantity = this.stockQuantity - stockQuantity;

        if (remainQuantity < 0) {
            throw new NotEnoughStockException("not enough stock quantity.");
        }

        this.stockQuantity = remainQuantity;
    }
}

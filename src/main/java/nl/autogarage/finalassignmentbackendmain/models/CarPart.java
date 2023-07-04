package nl.autogarage.finalassignmentbackendmain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carparts")
public class CarPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private Integer inStock;

    //    lijst met carpatrs
    @Enumerated(EnumType.STRING)
    private CarPartEnum carPartEnum;


//     relations

    @ManyToOne
    @JsonIgnore
    private Car car;


    public CarPart(CarPartEnum carPartEnum, Integer inStock) {
        this.carPartEnum = carPartEnum;
        this.inStock = inStock;
    }

}

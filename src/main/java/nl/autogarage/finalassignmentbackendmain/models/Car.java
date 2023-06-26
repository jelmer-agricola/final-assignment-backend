package nl.autogarage.finalassignmentbackendmain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    // in eindopdracht primary key maken van kenteken
    //@NotNull

    @Id
    @Column (nullable = false, unique = true)
    private String licenseplate;
    private String brand;
    private Integer mileage;

//    Carstatus

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarPart> carParts;


}

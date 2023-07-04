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


    @Id
    @Column(nullable = false, unique = true)
    private String licenseplate;
    @NotNull(message = "The licenseplate is not allowed to be Null ")
    private String brand;
    private Integer mileage;
    private String owner;

//    Carstatus
// Relaties
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarPart> carParts;


}

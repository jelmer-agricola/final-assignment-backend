package nl.autogarage.finalassignmentbackendmain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cars")


public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // in eindopdracht primary key maken van kenteken

    private Long id;
    @NotNull
    private String licenseplate;
    private String brand;
//    @Enumerated(EnumType.STRING)
//    private CarStatus carStatus




}

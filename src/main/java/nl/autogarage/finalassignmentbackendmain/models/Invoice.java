package nl.autogarage.finalassignmentbackendmain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double finalCost;
    public byte invoicePdf;

    private boolean paid;


    public static final double periodicVehicleInspection = 60.00;



    @OneToOne
    private Inspection inspection;
//    Carsatus repaired dus car car?

    //    user User
    @ManyToOne
    @JsonIgnore
    private Car car;

}

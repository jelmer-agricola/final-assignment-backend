package nl.autogarage.finalassignmentbackendmain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    public byte[] invoicePdf;
    private boolean paid;
    private LocalDate Date;
    private Double totalCostOfRepair;
    private Double finalCost;
    public static final double periodicVehicleInspection = 25.00;


    @OneToOne
    private Inspection inspection;

    @ManyToOne
    @JsonIgnore
    private Car car;





    public double calculateTotalCost() {
        double total = 0.0;
        // The repair price can only be calculated if the customer approved repairs
        if (inspection.isClientApproved()) {
//

            for (Repair repair : inspection.getRepairs()) {
                if (repair.isRepairFinished()) {
                    CarPart carPart = repair.getCarPart();
                    if (carPart != null) {
                        total += carPart.getCarPartCost();
                    }
                }
            }
        }
        return total;
    }

    public double calculateFinalCost() {
        double total = 0.0;
        total += periodicVehicleInspection;
        total += totalCostOfRepair;
        return total;
    }

}

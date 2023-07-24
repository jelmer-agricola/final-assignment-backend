package nl.autogarage.finalassignmentbackendmain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "inspection")
public class Inspection {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Double totalCostOfRepair;
    private String inspectionDescription;
//inspection date
    private boolean clientApproved;
    //    approval geeft de mechanic door
    private boolean inspectionFinished;

    @OneToMany(mappedBy = "inspection")
    List<Repair> repairs;

    @ManyToOne
    @JsonIgnore
    private Car car;

    @OneToOne(mappedBy = "inspection")
    @JsonIgnore
    private Invoice invoice;



//    public double calculateRepairCost() {
//        double total = 0.0;
//        // Totale kosten voor alle repairs
//        if (repairs != null) {
//            for (Repair repair : repairs) {
//                CarPart carPart = repair.getCarPart();
//                if (carPart != null) {
//                    total += carPart.getCarPartCost();
//                }
//            }
//        } else {
//            total = 0.0;
//        }
//        return total;
//    }


}

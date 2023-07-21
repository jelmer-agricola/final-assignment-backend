package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.models.Repair;

import java.util.List;

@Getter
@Setter
public class InspectionOutputDto {

    private Long id;
    private String inspectionDescription;
    private boolean clientApproved;
    private boolean inspectionFinished;

    @JsonIgnoreProperties(value = {"repairDescription"})
//    @JsonIgnoreProperties(value = {"repairDescription", "partRepairCost"})
    List<Repair> repairs;
    @JsonIgnore
    private Car car;
    @JsonIgnore
    private Invoice invoice;

    public double calculateRepairCost() {
        double total = 0.0;
        // Totale kosten voor alle repairs
        if (repairs != null) {
            for (Repair repair : repairs) {
                CarPart carPart = repair.getCarPart();
                if (carPart != null) {
                    total += carPart.getCarPartCost();
                }
            }
        } else {
            total = 0.0;
        }
        return total;
    }

}
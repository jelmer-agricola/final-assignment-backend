package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.models.Repair;

import java.util.List;

@Getter
@Setter
public class InspectionOutputDto {

    private Long id;
    private Long costEstimate;
    private String inspectionDescription;
    private boolean inspectionApproved;
    private boolean isInspected;

    @JsonIgnoreProperties(value = {"repairDescription"})
//    @JsonIgnoreProperties(value = {"repairDescription", "partRepairCost"})
    List<Repair> repairs;
    @JsonIgnore
    private Car car;
    @JsonIgnore
    private Invoice invoice;

}
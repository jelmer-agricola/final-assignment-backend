package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.models.Repair;

import java.util.List;

@Getter
@Setter
public class InspectionInputDto {

    private Long id;
    private Double costEstimate;
    private String inspectionDescription;
    private boolean inspectionApproved;
    private boolean isInspected;

    List<Repair> repairs;
    private Car car;
    private Invoice invoice;
}

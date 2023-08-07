package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

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
    private String inspectionDescription;
    private boolean clientApproved;
    private boolean inspectionFinished;

    List<Repair> repairs;
    private Car car;
    private Invoice invoice;
}

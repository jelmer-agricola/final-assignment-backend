package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionInputDto {

    private Long id;
    private Long costEstimate;
    private String inspectionDescription;
    private boolean repairApproved;

}

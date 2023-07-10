package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionOutputDto {

    private Long id;
    private Long costEstimate;
    private String inspectionDescription;
    private boolean  repairApproved;
    private String carPartStatus;

}
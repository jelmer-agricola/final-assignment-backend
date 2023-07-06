package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionOutputDto {

    private Long id;
    private Long costEstimate;
    private String description;
    private boolean  repairApproved;

}
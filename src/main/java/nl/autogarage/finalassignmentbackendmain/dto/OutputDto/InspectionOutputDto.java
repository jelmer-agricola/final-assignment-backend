package nl.autogarage.finalassignmentbackendmain.dto.OutputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionOutputDto {

    private Long id;
    private String inspectionCode;
    private String inspectorName;
    private String remarks;
    private boolean passed;

    // Getters and Setters (omitted for brevity)
}
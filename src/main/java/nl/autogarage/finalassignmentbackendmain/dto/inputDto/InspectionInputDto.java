package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionInputDto {

    @NotNull
    private String inspectionCode;
    private String inspectorName;
    private String remarks;
    private boolean passed;

    // Getters and Setters (omitted for brevity)
}

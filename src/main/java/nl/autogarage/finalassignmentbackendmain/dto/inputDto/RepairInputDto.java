package nl.autogarage.finalassignmentbackendmain.dto.inputDto;


import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;

@Getter
@Setter
public class RepairInputDto {

    private String repairDescription;
    private Double partRepairCost;
    private boolean repairFinished;
    private CarPart carPart;
    private Inspection inspection;


}

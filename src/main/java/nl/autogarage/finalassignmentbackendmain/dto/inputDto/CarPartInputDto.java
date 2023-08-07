package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;

@Setter
@Getter
public class CarPartInputDto {

    public CarPartEnum carPartEnum;
    private double carPartCost;
    public String partStatus;
    private boolean partIsInspected;

}

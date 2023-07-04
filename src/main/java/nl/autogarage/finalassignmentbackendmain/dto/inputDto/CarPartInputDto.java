package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;

@Setter
@Getter
public class CarPartInputDto {


    public CarPartEnum carPartEnum;
    public Integer inStock;


//    public String state;
//    public boolean checked;
}

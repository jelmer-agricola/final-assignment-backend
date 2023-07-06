package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;

import java.util.List;

@Getter
@Setter
public class CarPartOutputDto {
    private Long id;

    public CarPartEnum carPartEnum;
    public String partStatus;
    private List<CarPart> carParts;


//

//    public void setCarParts(List<CarPartOutputDto> carParts) {
//        this.carParts = carParts;
//    }
//    public String state;
//    public boolean checked;


}



package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;

@Getter
@Setter
public class CarPartOutputDto {

    public CarPartEnum carPartEnum;
    public Integer inStock;
//    public String state;
//    public boolean checked;


    //relations.........................................
//    @JsonIgnore
//    private Car car;
//    @JsonIgnore
}

package nl.autogarage.finalassignmentbackendmain.dto.outputDto;


import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;

import java.util.List;

@Getter
@Setter
public class CarOutputDto {


    private String licenseplate;
    private String brand;
    private Integer mileage;
    private String owner;

    private List<CarPart> carParts;


}


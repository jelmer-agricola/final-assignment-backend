package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;

import java.util.List;


@Getter
@Setter
public class CarInputDto {

    @NotNull
    private String licenseplate;
    private String brand;
    private Integer mileage;
    private String owner;

    public List<CarPart> carParts;



}

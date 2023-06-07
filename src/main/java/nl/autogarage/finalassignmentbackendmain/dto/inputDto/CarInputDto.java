package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CarInputDto {

    //@NotNull
    private String licenseplate;
    private String brand;


}

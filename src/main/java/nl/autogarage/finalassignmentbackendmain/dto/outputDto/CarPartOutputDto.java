package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.*;

import java.util.List;

@Getter
@Setter
public class CarPartOutputDto {
    private Long id;

    public CarPartEnum carPartEnum;
    public String partStatus;
    private boolean partIsInspected;
// Todo hier ook een cost estimate toevoegen?
    @JsonIgnore
    private Car car;


    @JsonIgnore
    List<Inspection> inspections;


//    public void setCarParts(List<CarPartOutputDto> carParts) {
//        this.carParts = carParts;
//    }
//    public String state;
//    public boolean checked;


}



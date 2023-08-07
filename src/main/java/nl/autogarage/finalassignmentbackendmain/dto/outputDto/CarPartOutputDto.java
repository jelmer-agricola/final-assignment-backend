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
    private double carPartCost;

    @JsonIgnore
    private Car car;
    @JsonIgnore
    List<Inspection> inspections;



}



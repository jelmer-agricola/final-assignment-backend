package nl.autogarage.finalassignmentbackendmain.dto.inputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairInputDto {

    private String description;
    private Long cost;
    private boolean repairFinished;


}

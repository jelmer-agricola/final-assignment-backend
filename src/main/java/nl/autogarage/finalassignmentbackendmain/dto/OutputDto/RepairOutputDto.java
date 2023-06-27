package nl.autogarage.finalassignmentbackendmain.dto.OutputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairOutputDto {

    private String description;
    private Long cost;
    private boolean repairFinished;


}

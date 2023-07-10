package nl.autogarage.finalassignmentbackendmain.dto.outputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairOutputDto {

//    in outputdto kan je id wel meegeven.
    private Long id;
    private String repairDescription;
    private Long finalCost;
    private boolean repairFinished;


}

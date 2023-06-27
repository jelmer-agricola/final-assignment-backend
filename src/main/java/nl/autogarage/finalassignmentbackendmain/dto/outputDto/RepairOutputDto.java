package nl.autogarage.finalassignmentbackendmain.dto.outputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairOutputDto {

//    in outputdto kan je id wel meegeven.
    private Long id;
    private String description;
    private Long cost;
    private boolean repairFinished;


}

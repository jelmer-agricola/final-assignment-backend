package nl.autogarage.finalassignmentbackendmain.dto.outputDto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;

@Getter
@Setter
public class RepairOutputDto {

//    in outputdto kan je id wel meegeven.
    private Long id;
    private String repairDescription;
    @JsonIgnore
    private CarPart carPart;
    private Long partRepairCost;
    private boolean repairFinished;
    @JsonIgnore
    private Inspection inspection;


    }


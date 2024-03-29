package nl.autogarage.finalassignmentbackendmain.dto.outputDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;

@Getter
@Setter
public class RepairOutputDto {

    private Long id;
    private String repairDescription;
    private boolean repairFinished;

    @JsonIgnore
    private Inspection inspection;

    @JsonIgnoreProperties (value = {"repairs", "partStatus"})
    private CarPart carPart;







}


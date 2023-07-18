package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.User;

@Getter
@Setter
public class InvoiceOutputDto {
    private Long id;
    private Double finalCost;
    private byte invoicePdf;
    private boolean paid;

    private Inspection inspection;
    @JsonIgnore
    private Car car;


    //    @JsonIgnore
//    private User user;
    // Constructors, getters, and setters
}


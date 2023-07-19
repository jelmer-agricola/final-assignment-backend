package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;

@Getter
@Setter
public class InvoiceInputDto {


    private Double finalCost;
//    public byte invoicePdf;
    private boolean paid;


    private Inspection inspection;
    private Car car;


    // Constructors, getters, and setters

//    private String licensseplate;
//    private User user;
//

}
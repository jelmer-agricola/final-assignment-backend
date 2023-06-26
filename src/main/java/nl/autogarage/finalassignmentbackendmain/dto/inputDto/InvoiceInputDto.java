package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
@Getter
@Setter
public class InvoiceInputDto {


    private Long repairCost;
    private boolean paid;

    // Constructors, getters, and setters

//    private String licensseplate;
//    private User user;
//
//    private Inspection inspection;
//    private Car car;
}
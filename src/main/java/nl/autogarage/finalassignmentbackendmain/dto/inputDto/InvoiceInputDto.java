package nl.autogarage.finalassignmentbackendmain.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;

import java.time.LocalDate;

@Getter
@Setter
public class InvoiceInputDto {

    private Double finalCost;
    public byte[] invoicePdf;
    private boolean paid;
    private LocalDate Date;
    private Inspection inspection;
    private Car car;

}
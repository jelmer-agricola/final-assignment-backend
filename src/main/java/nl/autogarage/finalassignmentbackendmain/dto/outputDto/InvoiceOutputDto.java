package nl.autogarage.finalassignmentbackendmain.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.User;

import java.time.LocalDate;

@Getter
@Setter
public class InvoiceOutputDto {
    private Long id;

    public byte[] invoicePdf;
    private boolean paid;
    private LocalDate Date;
    private Double finalCost;
    private Inspection inspection;
    @JsonIgnore
    private Car car;


}


package nl.autogarage.finalassignmentbackendmain.dto.OutputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceOutputDto {
    private Long id;
    private Long repairCost;
    private byte invoice;
    private boolean paid;

    // Constructors, getters, and setters
}


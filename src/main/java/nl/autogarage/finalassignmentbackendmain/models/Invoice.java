package nl.autogarage.finalassignmentbackendmain.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;
    private Long repairCost;
    private byte invoice ;
    private boolean paid;


//    Carsatus repaired dus car car?

//    user User
//


}

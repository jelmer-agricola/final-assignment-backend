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
@Table(name="inspection")
public class Inspection {


    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;
    private Long costEstimate;
    private String description;
   private boolean  repairApproved;



//    status verschillende onderdelen

//    repairs List Repair


}

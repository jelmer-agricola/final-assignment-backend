package nl.autogarage.finalassignmentbackendmain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inspection")
public class Inspection {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long costEstimate;
    private String description;
    private boolean repairApproved;

    @ManyToOne
    @JsonIgnore
    private CarPart carpart;

//    status verschillende onderdelen

//    repairs List Repair


}

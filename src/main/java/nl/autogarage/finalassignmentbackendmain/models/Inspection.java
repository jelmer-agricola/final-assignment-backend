package nl.autogarage.finalassignmentbackendmain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private String inspectionDescription;
    private boolean repairApproved;
//inspection date

    @OneToMany(mappedBy = "inspection")
    List<Repair> repairs;

    @ManyToOne
    @JsonIgnore
    private Car car;

    @OneToOne(mappedBy = "inspection")
    @JsonIgnore
    private Invoice invoice;




//    status verschillende onderdelen

//    repairs List Repair


}

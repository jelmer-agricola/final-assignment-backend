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
    private Double costEstimate;
    private String inspectionDescription;
//inspection date
//    approval krijgt de mechanic door
    private boolean inspectionApproved;
    private boolean inspectionFinished;


//    Bij aanmaken insepction alle carparts.isInspected op  false
//    inspectionFinished moet initieel ook op false staan

//    in de put in de service moet je het of true zetten

    //    in inspection ook een check zetten die staat standaard op false alleen als alle autoonderdleen isINspected
    //    true hebben dan kan inspectionFinished kan dan pas op true als alle repairs repairFinished staan


//    in  invoice alle dingen checken



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

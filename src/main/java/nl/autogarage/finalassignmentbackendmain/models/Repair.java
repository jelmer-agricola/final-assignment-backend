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
@Table(name = "repair")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String repairDescription;
//    ToDo partRepairCost misshcien naar carpart zodat een schatting naar de klant kan
    private Double partRepairCost;
//    In de post mag dit nu op true komen te staan
    private boolean repairFinished;



    @ManyToOne
    @JsonIgnore
    private CarPart carPart;

    @ManyToOne
    @JsonIgnore
    private Inspection inspection;



}

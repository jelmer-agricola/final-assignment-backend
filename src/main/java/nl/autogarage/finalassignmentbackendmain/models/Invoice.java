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
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long repairCost;
    private byte invoice;
    private boolean paid;

    @OneToOne
    private Inspection inspection;
//    Carsatus repaired dus car car?

    //    user User
    @ManyToOne
    @JsonIgnore
    private Car car;

}

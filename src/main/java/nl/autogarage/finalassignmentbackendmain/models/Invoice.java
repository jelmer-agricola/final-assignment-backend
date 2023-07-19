package nl.autogarage.finalassignmentbackendmain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double finalCost;
    @Lob
    @Type(type = "org.hibernate.annotations.Type")
    public byte[] invoicePdf;
    private boolean paid;

    public static final double periodicVehicleInspection = 60.00;


    @OneToOne
    private Inspection inspection;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Car car;

}

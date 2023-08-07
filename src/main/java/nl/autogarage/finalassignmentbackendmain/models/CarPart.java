package nl.autogarage.finalassignmentbackendmain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carparts")
public class CarPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String partStatus;
    private double carPartCost;
    @Value("${some.key:false}")
    public boolean partIsInspected;

    @Enumerated(EnumType.STRING)
    public CarPartEnum carPartEnum;

    @ManyToOne
    @JsonIgnore
    private Car car;

    @OneToMany(mappedBy = "carPart")
    private List<Repair> repairs;
}

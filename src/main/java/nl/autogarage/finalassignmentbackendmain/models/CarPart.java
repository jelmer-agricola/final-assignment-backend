package nl.autogarage.finalassignmentbackendmain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;

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

    @Enumerated(EnumType.STRING)
    public CarPartEnum carPartEnum;


//     relations

    @ManyToOne
    @JsonIgnore
    private Car car;

    @OneToMany(mappedBy = "carpart")
    private List<Repair> repairs;



//    public CarPart(CarPartEnum batteries) {
//    }


//    speciale setter om aan te geven dat er auto onderedelen worden toegevoegd.


}

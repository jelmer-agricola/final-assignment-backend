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
// TODO   Zet allle carparts is inspected op false ergens controle dit kan pas doorgezet worden als alle carparts op true staat
//  Deze check doen moet ik doen voor de invoice

    public boolean partIsInspected;
    //    in inspection ook een check zetten die staat standaard op false alleen als alle autoonderdleen isINspected true hebben dan kan inspectionFinished kan dan pas op true

    @Enumerated(EnumType.STRING)
    public CarPartEnum carPartEnum;


//     relations

    @ManyToOne
    @JsonIgnore
    private Car car;

    @OneToMany(mappedBy = "carPart")
    private List<Repair> repairs;



//    public CarPart(CarPartEnum batteries) {
//    }


//    speciale setter om aan te geven dat er auto onderedelen worden toegevoegd.


}

package nl.autogarage.finalassignmentbackendmain.repositories;

import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPartRepository  extends JpaRepository <CarPart, Long> {

}

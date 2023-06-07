package nl.autogarage.finalassignmentbackendmain.repositories;

import nl.autogarage.finalassignmentbackendmain.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository <Car, Long> {
}

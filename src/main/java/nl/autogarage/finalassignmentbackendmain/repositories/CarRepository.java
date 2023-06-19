package nl.autogarage.finalassignmentbackendmain.repositories;

import nl.autogarage.finalassignmentbackendmain.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository <Car, String> {


    Optional<Car> findByLicenseplate(String licenseplate);



//    boolean existsByLicensePlate(String licensePlate);
//
//    void deleteByLicensePlate(String licensePlate);
}

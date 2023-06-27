package nl.autogarage.finalassignmentbackendmain.repositories;

import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {


}

package nl.autogarage.finalassignmentbackendmain.repositories;

import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository <Invoice, Long>{


}

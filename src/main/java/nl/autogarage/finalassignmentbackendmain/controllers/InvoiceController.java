package nl.autogarage.finalassignmentbackendmain.controllers;

import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.service.InvoiceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/add/{inspection_id}")
    public ResponseEntity<String> createInvoice(@PathVariable long inspection_id) {
        long createdId = invoiceService.createInvoice(inspection_id);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/invoice/" + createdId).toUriString());
        return ResponseEntity.created(uri).body("invoice created linked to inspection with id " + inspection_id);

    }

    @GetMapping
    public ResponseEntity<Iterable<InvoiceOutputDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto> getInvoiceById(@PathVariable Long id) {
        InvoiceOutputDto invoiceOutputDto = invoiceService.getInvoiceById(id);
        if (invoiceOutputDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoiceOutputDto);
    }

    @GetMapping("/{licenseplate}/all")
    public ResponseEntity<List<InvoiceOutputDto>> getAllInvoicesByLicenseplate(@PathVariable String licenseplate) {
        List<InvoiceOutputDto> invoicesForCar = invoiceService.getAllInvoicesByLicensePlate(licenseplate);
        return ResponseEntity.ok(invoicesForCar);
    }

    @PutMapping("/{id}/uploadinvoicepdf")
    public ResponseEntity<String> uploadInvoicePdf(@PathVariable long id)
            throws IndexOutOfBoundsException {
        return ResponseEntity.ok(invoiceService.uploadInvoicePdf(id));
    }

    @GetMapping(value = "/{id}/download-pdfinvoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable long id) {
        return invoiceService.downloadInvoicePdf(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto> updateInvoicePaid(@PathVariable Long id, @RequestBody boolean paid) {
        InvoiceOutputDto updatedInvoice = invoiceService.updateInvoicePaid(id, paid);
        if (updatedInvoice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable Long id) {
        String message = invoiceService.deleteInvoice(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }
}

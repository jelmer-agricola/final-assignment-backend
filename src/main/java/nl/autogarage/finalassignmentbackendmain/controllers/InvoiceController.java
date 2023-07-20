package nl.autogarage.finalassignmentbackendmain.controllers;

import jakarta.validation.Valid;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InvoiceInputDto;
import nl.autogarage.finalassignmentbackendmain.service.InvoiceService;
import nl.autogarage.finalassignmentbackendmain.utils.ErrorUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

//    @PostMapping("/add")
//    public ResponseEntity<Object> createInvoice(@Valid @RequestBody InvoiceInputDto invoiceInputDto, BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors()) {
//            return ResponseEntity.badRequest().body(ErrorUtils.errorToStringHandling(bindingResult));
//        }
//        InvoiceOutputDto invoiceOutputDto = invoiceService.createInvoice(invoiceInputDto);
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + invoiceOutputDto.getId()).toUriString());
//        return ResponseEntity.created(uri).body(invoiceOutputDto);
//    }


    @PostMapping("/add/{inspection_id}")
    public ResponseEntity<String> createInvoice(@PathVariable long inspection_id) {
        long createdId = invoiceService.createInvoice(inspection_id);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/invoice/" + createdId).toUriString());
        return ResponseEntity.created(uri).body("invoice created linked to inspection with id " + inspection_id);

    }

    @GetMapping("")
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

//  Todo getmapping voor  allinvoices from user

    @PutMapping("{id}/generateinvoicepdf")
    public ResponseEntity<String> generateInvoicePdf(@PathVariable long id) throws IOException {
        return ResponseEntity.ok(invoiceService.generateInvoicePdf(id));
    }

    @GetMapping(value = "/{id}/getpdfinvoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getInvoicePdf(@PathVariable long id) {
        return invoiceService.getInvoicePdf(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto> updateInvoice(@PathVariable Long id, @RequestBody InvoiceOutputDto invoiceOutputDto) {
        invoiceOutputDto.setId(id);
        InvoiceOutputDto updatedInvoice = invoiceService.updateInvoice(id, invoiceOutputDto);
        if (updatedInvoice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedInvoice);
    }
//    Todo generatepdf of invoice in put

    //    Todo put voor invoice paid !!
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable Long id) {
        String message = invoiceService.deleteInvoice(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }


}

package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InvoiceInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.repositories.InspectionRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InspectionRepository inspectionRepository;
//    userrepository

    public InvoiceService(InvoiceRepository invoiceRepository, InspectionRepository inspectionRepository) {
        this.invoiceRepository = invoiceRepository;
        this.inspectionRepository = inspectionRepository;
    }


//    deze waarden initieel op false

//     check voor repairsFinished
//     check voor inspectionFinished  GEDAAN
//     check voor carpart isChecked

// Todo voor 17-7 aanmaken nieuwe create invoice

    public Long createInvoice(long inspection_id) {
        Optional<Inspection> optionalCarInspection = inspectionRepository.findById(inspection_id);
        if (optionalCarInspection.isEmpty()) {
            throw new RecordNotFoundException("no Inspection found with id: " + inspection_id);
//            is inspected dus og
        } else if (!optionalCarInspection.get().isInspectionFinished()) {
//            bad request???
            throw new RecordNotFoundException("The inspection is not yet finished it still needs to be finished. No invoice can be created.");
        } else {
            Inspection inspection = optionalCarInspection.get();
            Invoice newInvoice = new Invoice();
            newInvoice.setPaid(false);
            newInvoice.setInspection(inspection);
            newInvoice.setCar(inspection.getCar());
//            newInvoice.setUser

            newInvoice.setFinalCost(newInvoice.getFinalCost());

            Invoice savedInvoice = invoiceRepository.save(newInvoice);

            return savedInvoice.getId();
        }
    }


//    public InvoiceOutputDto createInvoice(InvoiceInputDto invoiceInputDto) {
//        Invoice invoice = transferInputDtoToInvoice(invoiceInputDto);
//        Invoice savedInvoice = invoiceRepository.save(invoice);
//        return transferInvoiceToOutputDto(savedInvoice);
//    }
//


    public List<InvoiceOutputDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceOutputDto> invoiceOutputDtos = new ArrayList<>();
        for (Invoice invoice : invoices) {
            invoiceOutputDtos.add(transferInvoiceToOutputDto(invoice));
        }
        return invoiceOutputDtos;
    }


    //     hier heel veel logica toevoegen over de pdf
    public InvoiceOutputDto getInvoiceById(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            return transferInvoiceToOutputDto(invoice);
        } else {
            throw new RecordNotFoundException("Invoice not found with ID " + id);
        }
    }


    public InvoiceOutputDto updateInvoice(long id, InvoiceOutputDto invoiceOutputDto) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new RecordNotFoundException("No Invoice with id: " + id);
        } else {
            Invoice updatedInvoice = optionalInvoice.get();
            updatedInvoice.setPaid(invoiceOutputDto.isPaid());
            Invoice savedInvoice = invoiceRepository.save(updatedInvoice);
            return transferInvoiceToOutputDto(savedInvoice);
        }
    }

    public String deleteInvoice(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return "Invoice with ID: " + id + " has been deleted.";
        }
        throw new RecordNotFoundException("Invoice with ID " + id + " does not exist");
    }


    private Invoice transferInputDtoToInvoice(InvoiceInputDto invoiceInputDto) {
        Invoice invoice = new Invoice();
        invoice.setFinalCost(invoiceInputDto.getFinalCost());
//        invoice.setInvoice(invoiceInputDto.getInvoice());
        invoice.setPaid(invoiceInputDto.isPaid());
        return invoice;
    }

    private InvoiceOutputDto transferInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto outputDto = new InvoiceOutputDto();
        outputDto.setId(invoice.getId());
        outputDto.setFinalCost(invoice.getFinalCost());
        outputDto.setInvoicePdf(invoice.getInvoicePdf());
        outputDto.setPaid(invoice.isPaid());
        return outputDto;
    }


}

package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InvoiceInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.InvoiceAlreadyExistsException;
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

    public Long createInvoice(long inspection_id) {
        Optional<Inspection> optionalCarInspection = inspectionRepository.findById(inspection_id);
        if (optionalCarInspection.isEmpty()) {
            throw new RecordNotFoundException("no Inspection found with id: " + inspection_id);
//            is inspected dus og
        } else if (!optionalCarInspection.get().isInspectionFinished()) {
//            bad request???
            throw new RecordNotFoundException("The inspection is not yet finished it still needs to be finished. No invoice can be created.");
        } else if (invoiceRepository.existsByInspectionId(inspection_id)) {
            throw new InvoiceAlreadyExistsException("An invoice already exists for this inspection with id " + inspection_id);
        } else {
            Inspection inspection = optionalCarInspection.get();
            Invoice newInvoice = new Invoice();

            newInvoice.setInspection(inspection);

            newInvoice.setPaid(false);
            newInvoice.setCar(inspection.getCar());
//            newInvoice.setUser

            newInvoice.setFinalCost(newInvoice.getFinalCost());

            Invoice savedInvoice = invoiceRepository.save(newInvoice);

            return savedInvoice.getId();
        }
    }




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

//    Todo get allinvoices fromUser
//    Todo GeneratePdf


//  Todo  GetpdfInvoice

    public InvoiceOutputDto updateInvoice(long id, InvoiceOutputDto invoiceOutputDto) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new RecordNotFoundException("No Invoice with id: " + id);
        } else {
            Invoice updatedInvoice = optionalInvoice.get();
            updatedInvoice.setPaid(invoiceOutputDto.isPaid());
            updatedInvoice.setFinalCost(invoiceOutputDto.getFinalCost());
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
        invoice.setInvoicePdf(invoiceInputDto.getInvoicePdf());
        invoice.setPaid(invoiceInputDto.isPaid());
        invoice.setInspection(invoiceInputDto.getInspection());

        return invoice;
    }

    private InvoiceOutputDto transferInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto invoiceOutputDto = new InvoiceOutputDto();
        invoiceOutputDto.setId(invoice.getId());
        invoiceOutputDto.setFinalCost(invoice.getFinalCost());
        invoiceOutputDto.setInvoicePdf(invoice.getInvoicePdf());
        invoiceOutputDto.setPaid(invoice.isPaid());
        invoiceOutputDto.setInspection(invoice.getInspection());
        return invoiceOutputDto;
    }


}

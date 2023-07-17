package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InvoiceInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }


//    deze waarden initieel op false

//     check voor repairsFinished
//     check voor inspectionFinished
//     check voor carpart isChecked



    public InvoiceOutputDto createInvoice(InvoiceInputDto invoiceInputDto) {
        Invoice invoice = transferInputDtoToInvoice(invoiceInputDto);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return transferInvoiceToOutputDto(savedInvoice);
    }

    public List<InvoiceOutputDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List <InvoiceOutputDto> invoiceOutputDtos = new ArrayList<>();
        for (Invoice invoice: invoices){
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
        outputDto.setInvoice(invoice.getInvoice());
        outputDto.setPaid(invoice.isPaid());
        return outputDto;
    }



}

package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.InvoiceOutputDto;
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

    public InvoiceOutputDto createInvoice(InvoiceInputDto invoiceInputDto) {
        Invoice invoice = transferInputDtoToInvoice(invoiceInputDto);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return transferInvoiceToOutputDto(savedInvoice);
    }

    public List<InvoiceOutputDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return transferInvoiceListToOutputDtoList(invoices);
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




    public InvoiceOutputDto updateInvoice(InvoiceOutputDto invoiceOutputDto) {
        Long id = invoiceOutputDto.getId();
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Invoice with ID " + id + " does not exist"));
        Invoice updatedInvoice = transferOutputDtoToInvoice(invoiceOutputDto, existingInvoice);
        Invoice savedInvoice = invoiceRepository.save(updatedInvoice);
        return transferInvoiceToOutputDto(savedInvoice);
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
        invoice.setRepairCost(invoiceInputDto.getRepairCost());
//        invoice.setInvoice(invoiceInputDto.getInvoice());
        invoice.setPaid(invoiceInputDto.isPaid());
        return invoice;
    }

    private InvoiceOutputDto transferInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto outputDto = new InvoiceOutputDto();
        outputDto.setId(invoice.getId());
        outputDto.setRepairCost(invoice.getRepairCost());
        outputDto.setInvoice(invoice.getInvoice());
        outputDto.setPaid(invoice.isPaid());
        return outputDto;
    }

    private List<InvoiceOutputDto> transferInvoiceListToOutputDtoList(List<Invoice> invoices) {
        List<InvoiceOutputDto> outputDtoList = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceOutputDto outputDto = transferInvoiceToOutputDto(invoice);
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

    private Invoice transferOutputDtoToInvoice(InvoiceOutputDto invoiceOutputDto, Invoice existingInvoice) {
        existingInvoice.setRepairCost(invoiceOutputDto.getRepairCost());
        existingInvoice.setInvoice(invoiceOutputDto.getInvoice());
        existingInvoice.setPaid(invoiceOutputDto.isPaid());
        return existingInvoice;
    }
}

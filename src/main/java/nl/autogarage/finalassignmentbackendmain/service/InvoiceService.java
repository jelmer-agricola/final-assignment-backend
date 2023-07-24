package nl.autogarage.finalassignmentbackendmain.service;


import com.lowagie.text.Font;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InvoiceInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.InvoiceAlreadyExistsException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.models.Repair;
import nl.autogarage.finalassignmentbackendmain.repositories.InspectionRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.InvoiceRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
            throw new BadRequestException("The inspection is not yet finished it still needs to be finished. No invoice can be created.");
        } else if (invoiceRepository.existsByInspectionId(inspection_id)) {
            throw new InvoiceAlreadyExistsException("An invoice already exists for this inspection with id " + inspection_id);
        } else {
            Inspection inspection = optionalCarInspection.get();
            Invoice newInvoice = new Invoice();

            newInvoice.setInspection(inspection);

            newInvoice.setPaid(false);
            newInvoice.setCar(inspection.getCar());
//            newInvoice.setUser
            newInvoice.setDate(java.time.LocalDate.now());

//
            newInvoice.setTotalCostOfRepair(newInvoice.calculateTotalCost());
            newInvoice.setFinalCost(newInvoice.calculateFinalCost());


            Invoice savedInvoice = invoiceRepository.save(newInvoice);

            return savedInvoice.getId();
        }
    }




    public Iterable<InvoiceOutputDto> getAllInvoices() {
        ArrayList<InvoiceOutputDto> invoiceOutputDtos = new ArrayList<>();
        Iterable<Invoice> allInvoices = invoiceRepository.findAll();
        for (Invoice a : allInvoices) {
            InvoiceOutputDto invoiceDto = transferInvoiceToOutputDto(a);
            invoiceOutputDtos.add(invoiceDto);
        }
        return invoiceOutputDtos;
    }



    public InvoiceOutputDto getInvoiceById(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            return transferInvoiceToOutputDto(invoice);
        } else {
            throw new RecordNotFoundException("Invoice not found with ID " + id);
        }
    }

//    Todo get allinvoices fromUser of van licenseplaat?


public String generateInvoicePdf(long id) throws IndexOutOfBoundsException {
    Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("no invoice found with id " + id));

    Document invoicePdf = new Document(PageSize.A4);
    ByteArrayOutputStream pdfOutputStream  = new ByteArrayOutputStream();
    PdfWriter.getInstance(invoicePdf, pdfOutputStream );

    invoicePdf.open();
    //used font styles
    Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
    fontTitle.setSize(20);
    Font fontInfo = FontFactory.getFont(FontFactory.HELVETICA);
    fontInfo.setSize(9);
    Font fontSection = FontFactory.getFont(FontFactory.HELVETICA);
    fontSection.setSize(11);
//    Font lines = FontFactory.getFont(FontFactory.HELVETICA);
//    lines.setSize(20);

    // actual text on the pdf

//    Company info
    Paragraph paragraph = new Paragraph("Garage OkaySjon\nEmail: okaysjon@garage.nl\n\nPostbus 0906BA Utrecht\nTelefoonnummer: 06-87654321", fontInfo);
    paragraph.setAlignment(Element.ALIGN_LEFT);

    Paragraph paragraph1 = new Paragraph("Factuur\n", fontTitle);
    paragraph1.setAlignment(Paragraph.ALIGN_LEFT);

    //Invoice info
//    Paragraph paragraph2 = new Paragraph("Customer: " + invoice.getUser().getUsername() + "\t" + "\t" + "\t" +
//            "Date: " + invoice.getDate() + "\t" + "\t" + "\t" +
//            "Invoice ID: " + invoice.getId() + "\t" + "\t" + "\t" +
//            "License plate: " + invoice.getCar().getLicenseplate(), fontSection);
//    paragraph2.setAlignment(Paragraph.ALIGN_CENTER);




    Paragraph paragraph3 = new Paragraph(repairItemStringBuilder(invoice), fontSection);
    paragraph3.setAlignment(Element.ALIGN_LEFT);




    Paragraph paragraph4 = new Paragraph(
            "Totaal aan reparatie kosten: " + invoice.getTotalCostOfRepair() +
                    "\n" + "Algemene Periodieke Keuring: " + Invoice.periodicVehicleInspection +
                    "\n" + "Totaal bedrag : " + (invoice.getFinalCost()),
            fontSection);
    paragraph4.setAlignment(Paragraph.ALIGN_RIGHT);

    invoicePdf.add(paragraph);
    invoicePdf.add(paragraph1);
//    document.add(paragraph2);
    invoicePdf.add(paragraph3);
    invoicePdf.add(paragraph4);
    invoicePdf.close();

    PdfWriter.getInstance(invoicePdf, pdfOutputStream ).close();
// Todo User hier nog aan toevoegen regel hieronder is wat er eigenlijk in moet Als de user dus klaar is

//    String filename = invoice.getUser().getUsername() + id + " Invoice.pdf";
    String filename = invoice.getId()+ id + " Invoice.pdf";
    byte[] pdfinvoice = pdfOutputStream .toByteArray();
    invoice.setInvoicePdf(pdfinvoice);
    invoiceRepository.save(invoice);
    return filename + " created and stored to the database";
}

//  Todo  GetpdfInvoice  Geeft no pdf available for this invoice.

    public ResponseEntity<byte[]> getInvoicePdf(long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No car invoice found with id: " + id));
        byte[] invoicePdf = invoice.getInvoicePdf();
        if (invoicePdf == null){
            throw new RecordNotFoundException("no pdf available for this invoice.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice" + id + ".pdf");
        headers.setContentLength(invoicePdf.length);
        return new ResponseEntity<>(invoicePdf, headers, HttpStatus.OK);
    }


    public InvoiceOutputDto updateInvoicePaid(long id, boolean paid) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new RecordNotFoundException("No Invoice with id: " + id);
        } else {
            Invoice updatedInvoice = optionalInvoice.get();
            updatedInvoice.setPaid(paid);

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
        invoice.setDate(invoiceInputDto.getDate());
        invoice.setTotalCostOfRepair(invoiceInputDto.getFinalCost());

        return invoice;
    }

    private InvoiceOutputDto transferInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto invoiceOutputDto = new InvoiceOutputDto();
        invoiceOutputDto.setId(invoice.getId());
        invoiceOutputDto.setFinalCost(invoice.getFinalCost());
        if (invoice.getTotalCostOfRepair() != 0.0) {
            invoiceOutputDto.setFinalCost(invoice.getFinalCost());
        }
        invoiceOutputDto.setInvoicePdf(invoice.getInvoicePdf());
        invoiceOutputDto.setPaid(invoice.isPaid());
        invoiceOutputDto.setInspection(invoice.getInspection());

        if (invoice.getDate() != null) {
            invoiceOutputDto.setDate(invoice.getDate());
        }
        return invoiceOutputDto;
    }

    public String repairItemStringBuilder(Invoice invoice) {
        StringBuilder repairitems = new StringBuilder();
        for (Repair repair : invoice.getInspection().getRepairs()) {
            boolean repairFinished = repair.isRepairFinished();
            String repairStatus = repairFinished ? "afgerond" : "niet afgerond";
            String carPartName = repair.getCarPart().getCarPartEnum().getTranslatedName().toLowerCase();

            repairitems.append("Auto onderdeel: ").append(carPartName).append("\t\t\t")
                    .append(" Reparatie kosten: €").append(repair.getCarPart().getCarPartCost()).append("\t\t\t")
                    .append(" Reparatie ").append(repairStatus).append(" \n")
                    .append("Beschrijving: ").append(repair.getRepairDescription()).append("\n \n");
        }
        repairitems.append("Algemene Periodieke Keuring \t\t\t" + Invoice.periodicVehicleInspection + "\t\t\t voldaan");
        return repairitems.toString();
    }

}

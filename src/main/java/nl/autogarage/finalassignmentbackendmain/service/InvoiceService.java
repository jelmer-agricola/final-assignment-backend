package nl.autogarage.finalassignmentbackendmain.service;


import com.lowagie.text.Font;
import com.lowagie.text.Image;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InvoiceInputDto;
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


import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
            newInvoice.setDate(java.time.LocalDate.now());

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
public String generateInvoicePdf(long id) throws IOException {
    Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("no invoice found with id " + id));

    Document document = new Document(PageSize.A4);
    ByteArrayOutputStream pdfOutputStream  = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, pdfOutputStream );

    document.open();
    //used font styles
    Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    fontTitle.setSize(22);
    Font fontparagraphinfo = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    fontparagraphinfo.setSize(8);
    Font fontparagraph = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    fontparagraph.setSize(10);
    Font lines = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    lines.setSize(22);

    // actual text on the pdf

      // adress info
    Paragraph paragraph = new Paragraph("GARAGE OkaySjon\n  Paleis Noordeinde\n 2500 GK Den Haag\n Phone: 030-12345678 \n Email: SomeFake@Adres.com", fontparagraphinfo);
    paragraph.setAlignment(Element.ALIGN_LEFT);

    //title
    Paragraph paragraph1 = new Paragraph("INVOICE\n", fontTitle);
    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);

    //Invoice info
    Paragraph paragraph2 = new Paragraph("Costumer: " + invoice.getUser().getUsername() + "\t" + "\t" + "\t" +
            "Date: " + invoice.getDate() + "\t" + "\t" + "\t" +
            "Invoice ID: " + invoice.getId() + "\t" + "\t" + "\t" +
            "License plate: " + invoice.getCar().getLicenseplate(), fontparagraph);
    paragraph2.setAlignment(Paragraph.ALIGN_CENTER);


    Paragraph paragraph3 = new Paragraph("-----------------------------------------------------------------------", lines);
    paragraph3.setAlignment(Paragraph.ALIGN_TOP);

    Paragraph paragraph4 = new Paragraph(repairItemStringBuilder(invoice), fontparagraph);
    paragraph4.setAlignment(Element.ALIGN_LEFT);


    Paragraph paragraph5 = new Paragraph("-----------------------------------------------------------------------", lines);
    paragraph5.setAlignment(Paragraph.ALIGN_TOP);

//    Paragraph paragraph6 = new Paragraph(
//            "Total repair cost: " + invoice.getTotalrepaircost() +
//                    "\n" + "APK: " + Invoice.APKCHECK +
//                    "\n" + "Total cost without tax: " + (invoice.getTotalrepaircost() + Invoice.APKCHECK) +
//                    "\n" + "Tax: " + Invoice.btw + " %" +
//                    "\n" + "Total cost after tax: " + invoice.getTotalcost(), fontparagraph);
//    paragraph6.setAlignment(Paragraph.ALIGN_RIGHT);

    document.add(paragraph);
    document.add(paragraph1);
    document.add(paragraph2);
    document.add(paragraph3);
    document.add(paragraph4);
    document.add(paragraph5);
//    document.add(paragraph6);
    document.close();

    PdfWriter.getInstance(document, pdfOutputStream ).close();
    String filename = invoice.getUser().getUsername() + id + "Invoice.pdf";
    byte[] pdfinvoice = pdfOutputStream .toByteArray();
    invoice.setInvoicePdf(pdfinvoice);
    invoiceRepository.save(invoice);
    return filename + " made and stored to the database";
}

//  Todo  GetpdfInvoice TESTEN Geeft op het moent een 500 error

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
//        invoice.setInvoicePdf(invoiceInputDto.getInvoicePdf());
        invoice.setPaid(invoiceInputDto.isPaid());
        invoice.setInspection(invoiceInputDto.getInspection());
        invoice.setDate(invoiceInputDto.getDate());



        return invoice;
    }

    private InvoiceOutputDto transferInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto invoiceOutputDto = new InvoiceOutputDto();
        invoiceOutputDto.setId(invoice.getId());
        invoiceOutputDto.setFinalCost(invoice.getFinalCost());
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
            repairitems.append("Carpart: ").append(repair.getCarPart().getCarPartEnum()).append("\t\t\t").append("Repair-cost: ").append(repair.getPartRepairCost()).append("\t\t\t").append("Repair-done: ").append(repair.isRepairFinished()).append(" \n").append("Notes: ").append(repair.getRepairDescription()).append("\n \n");
        }
        repairitems.append("APK CHECK \t\t\t" + Invoice.periodicVehicleInspection + "\t\t\tvoldaan");
        return repairitems.toString();
    }
}

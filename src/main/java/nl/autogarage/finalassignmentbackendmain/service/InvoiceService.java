package nl.autogarage.finalassignmentbackendmain.service;

import com.lowagie.text.Font;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InvoiceOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.InvoiceAlreadyExistsException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.models.Repair;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
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
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InspectionRepository inspectionRepository;
    private final CarRepository carRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, InspectionRepository inspectionRepository, CarRepository carRepository) {
        this.invoiceRepository = invoiceRepository;
        this.inspectionRepository = inspectionRepository;
        this.carRepository = carRepository;
    }

    public Long createInvoice(long inspection_id) {
        Optional<Inspection> optionalCarInspection = inspectionRepository.findById(inspection_id);
        if (optionalCarInspection.isEmpty()) {
            throw new RecordNotFoundException("no Inspection found with id: " + inspection_id);
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
            newInvoice.setDate(java.time.LocalDate.now());
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

    public List<InvoiceOutputDto> getAllInvoicesByLicensePlate(String licensePlate) {
        List<InvoiceOutputDto> invoicesForCar = new ArrayList<>();
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licensePlate);

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            for (Invoice invoice : car.getInvoices()) {
                InvoiceOutputDto invoiceDto = transferInvoiceToOutputDto(invoice);
                invoicesForCar.add(invoiceDto);
            }
        } else {
            throw new RecordNotFoundException("Car not found with license plate: " + licensePlate);
        }

        return invoicesForCar;
    }


    public String uploadInvoicePdf(long id) throws IndexOutOfBoundsException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("no invoice found with id " + id));

        Document invoicePdf = new Document(PageSize.A4);
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(invoicePdf, pdfOutputStream);

        invoicePdf.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(20);
        Font fontInfo = FontFactory.getFont(FontFactory.HELVETICA);
        fontInfo.setSize(9);
        Font fontSection = FontFactory.getFont(FontFactory.HELVETICA);
        fontSection.setSize(11);

        Paragraph paragraph = new Paragraph("Garage OkaySjon\nEmail: okaysjon@garage.nl\nPostbus 0906BA Utrecht\nTelefoonnummer: 06-87654321", fontInfo);
        paragraph.setAlignment(Element.ALIGN_LEFT);

        Paragraph paragraph1 = new Paragraph("Factuur", fontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph2 = new Paragraph(
                "Klant: " + invoice.getCar().getOwner() + "\n" +
                        "Date: " + invoice.getDate() + "\n" +
                        "Factuurnummer: " + invoice.getId() + "\n" +
                        "Kenteken: " + invoice.getCar().getLicenseplate(), fontSection);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        paragraph1.setSpacingAfter(10);
        paragraph2.setSpacingAfter(10);

        Paragraph paragraph3 = new Paragraph(repairItemStringBuilder(invoice), fontSection);
        paragraph3.setAlignment(Element.ALIGN_LEFT);


        Paragraph paragraph4 = new Paragraph(
                "Totaal aan reparatie kosten: €" + invoice.getTotalCostOfRepair() +
                        "\n" + "Algemene Periodieke Keuring: €" + Invoice.periodicVehicleInspection +
                        "\n" + "Totaal bedrag : €" + (invoice.getFinalCost()),
                fontSection);
        paragraph4.setAlignment(Paragraph.ALIGN_RIGHT);

        invoicePdf.add(paragraph);
        invoicePdf.add(paragraph1);
        invoicePdf.add(paragraph2);
        invoicePdf.add(paragraph3);
        invoicePdf.add(paragraph4);
        invoicePdf.close();

        PdfWriter.getInstance(invoicePdf, pdfOutputStream).close();
        String filename = invoice.getId() + id + " Invoice.pdf";
        byte[] pdfinvoice = pdfOutputStream.toByteArray();
        invoice.setInvoicePdf(pdfinvoice);
        invoiceRepository.save(invoice);
        return filename + " created";
    }

    public ResponseEntity<byte[]> downloadInvoicePdf(long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No car invoice found with id: " + id));
        byte[] invoicePdf = invoice.getInvoicePdf();
        if (invoicePdf == null) {
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
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Invoice with ID " + id + " does not exist"));

        if (invoice.isPaid()) {
            invoiceRepository.deleteById(id);
            return "Invoice with ID: " + id + " has been deleted.";
        } else {
            throw new BadRequestException("Invoice with ID " + id + " has not been paid and cannot be deleted.");
        }
    }

    private InvoiceOutputDto transferInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto invoiceOutputDto = new InvoiceOutputDto();
        invoiceOutputDto.setId(invoice.getId());
        invoiceOutputDto.setFinalCost(invoice.getFinalCost());
        invoiceOutputDto.setCar(invoice.getCar());
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
            double carPartCost = repair.getCarPart().getCarPartCost();

            if (carPartCost > 0) {
                repairitems.append("Auto onderdeel: ").append(carPartName).append("\t\t\t")
                        .append(" Reparatie kosten: €").append(carPartCost).append("\t\t\t")
                        .append(" Reparatie ").append(repairStatus).append(" \n")
                        .append("Beschrijving: ").append(repair.getRepairDescription()).append("\n \n");
            }
        }
        repairitems.append("Algemene Periodieke Keuring €\t\t\t" + Invoice.periodicVehicleInspection + "\t\t\t voldaan");
        return repairitems.toString();
    }
}

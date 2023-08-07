package nl.autogarage.finalassignmentbackendmain.controllers;

import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InspectionOutputDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nl.autogarage.finalassignmentbackendmain.service.InspectionService;

import java.util.List;

@RestController
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping("/add/{licenseplate}")
    public ResponseEntity<InspectionOutputDto> createInspection(@PathVariable String licenseplate) {
        return ResponseEntity.ok(inspectionService.createInspection(licenseplate));
    }

    @GetMapping
    public ResponseEntity<List<InspectionOutputDto>> getAllInspections() {
        return ResponseEntity.ok().body(inspectionService.getAllInspections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectionOutputDto> getInspectionById(@PathVariable Long id) {
        InspectionOutputDto inspectionOutputDto = inspectionService.getInspectionById(id);
        if (inspectionOutputDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inspectionOutputDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectionOutputDto> updateInspection(@PathVariable Long id, @RequestBody InspectionOutputDto inspectionOutputDto) {
        inspectionOutputDto.setId(id);
        InspectionOutputDto updatedInspection = inspectionService.updateInspection(id, inspectionOutputDto);
        if (updatedInspection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedInspection);
    }

    @PatchMapping("/{id}/client-approval")
    public ResponseEntity<InspectionOutputDto> clientApproval(@PathVariable Long id, @RequestBody boolean clientApproved) {
        InspectionOutputDto updatedInspection = inspectionService.clientApproval(id, clientApproved);
        return ResponseEntity.ok(updatedInspection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInspection(@PathVariable Long id) {
        String message = inspectionService.deleteInspection(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }
}
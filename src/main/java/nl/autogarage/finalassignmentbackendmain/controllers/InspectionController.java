package nl.autogarage.finalassignmentbackendmain.controllers;

import nl.autogarage.finalassignmentbackendmain.utils.ErrorUtils;

import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InspectionOutputDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InspectionInputDto;
import nl.autogarage.finalassignmentbackendmain.service.InspectionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

//    @PostMapping("/add")
//    public ResponseEntity<Object> createInspection(@Valid @RequestBody InspectionInputDto inspectionInputDto, BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors()) {
//            return ResponseEntity.badRequest().body(ErrorUtils.errorToStringHandling(bindingResult));
//        }
//        InspectionOutputDto inspectionOutputDto = inspectionService.createInspection(inspectionInputDto);
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + inspectionOutputDto.getId()).toUriString());
//        return ResponseEntity.created(uri).body(inspectionOutputDto);
//    }

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

    //     PUTMAPPING VOOR MECHANIC hieronder wordt insepction approved aangegeven
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
    public ResponseEntity<InspectionOutputDto> clientApproval(@PathVariable Long id, @RequestBody InspectionOutputDto inspectionOutputDto
    ) {
        InspectionOutputDto updatedInspection = inspectionService.clientApproval(id, inspectionOutputDto);
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
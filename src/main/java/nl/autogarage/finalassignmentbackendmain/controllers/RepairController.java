package nl.autogarage.finalassignmentbackendmain.controllers;
import nl.autogarage.finalassignmentbackendmain.utils.ErrorUtils;

import jakarta.validation.Valid;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.service.RepairService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/repair")
public class RepairController {

    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }


    @PostMapping("/add")
    public ResponseEntity<Object> createRepair(@Valid @RequestBody RepairInputDto repairInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.errorToStringHandling(bindingResult));
        }

        RepairOutputDto repairOutputDto = repairService.createRepair(repairInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + repairOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(repairOutputDto);
    }


    @GetMapping
    public ResponseEntity<List<RepairOutputDto>> getAllRepair() {
        return ResponseEntity.ok().body(repairService.getAllRepair());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairOutputDto> getRepairById(@PathVariable Long id) {
        RepairOutputDto repairOutputDto = repairService.getRepairById(id);
        if (repairOutputDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repairOutputDto);
    }
// update

    @PutMapping("/{id}")
    public ResponseEntity<RepairOutputDto> updateRepair(@PathVariable Long id, @RequestBody RepairOutputDto repairOutputDto){
        repairOutputDto.setId(id);
        RepairOutputDto updatedRepair = repairService.updateRepair(id, repairOutputDto);
        if (updatedRepair == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRepair);
    }







//    @PutMapping("/{id}")
//    public ResponseEntity<InspectionOutputDto> updateInspection(@PathVariable Long id, @RequestBody InspectionOutputDto inspectionOutputDto) {
//        inspectionOutputDto.setId(id);
//        InspectionOutputDto updatedInspection = inspectionService.updateInspection(id, inspectionOutputDto);
//        if (updatedInspection == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(updatedInspection);
//    }


//    delete


//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteInspection(@PathVariable Long id) {
//        String message = inspectionService.deleteInspection(id);
//        if (message == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(message);
//    }


}

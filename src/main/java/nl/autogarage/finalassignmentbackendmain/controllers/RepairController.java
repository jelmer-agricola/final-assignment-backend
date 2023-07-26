package nl.autogarage.finalassignmentbackendmain.controllers;

import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;
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
import java.util.Map;


@RestController
@RequestMapping("/repair")
public class RepairController {

    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    // ipv inspection_id
    @PostMapping("/add/{carpart}/{inspection_id}")
    public ResponseEntity<String> createRepair(@PathVariable String carpart, @PathVariable long inspection_id, @Valid @RequestBody RepairInputDto repairInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.errorToStringHandling(bindingResult));
        } else {
            long createdId = repairService.createRepair(repairInputDto, carpart, inspection_id);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/repairs/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("Repair has been added to inspection " + inspection_id);
        }
    }

    @PostMapping("/carparts/{inspection_id}")
    public ResponseEntity<Map<Long, CarPartEnum>> createRepairsForAllCarPartsInInspection(@PathVariable long inspection_id) {
        Map<Long, CarPartEnum> repairIdsAndCarPartEnums = repairService.createRepairsForAllCarPartsInInspection(inspection_id);
        return ResponseEntity.ok(repairIdsAndCarPartEnums);
    }

    @GetMapping
    public ResponseEntity<List<RepairOutputDto>> getAllRepair() {
        return ResponseEntity.ok().body(repairService.getAllRepair());
    }

    @GetMapping("/lp/{licenseplate}")
    public ResponseEntity<Iterable<RepairOutputDto>> getAllRepairsFromLicenceplate(@PathVariable String licenseplate) {
        return ResponseEntity.ok().body(repairService.getAllRepairsFromLicenceplate(licenseplate));
    }


    @GetMapping("/{id}")
    public ResponseEntity<RepairOutputDto> getRepairById(@PathVariable Long id) {
        RepairOutputDto repairOutputDto = repairService.getRepairById(id);
        if (repairOutputDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repairOutputDto);
    }

    @PatchMapping("part_repaired/{id}")
    public ResponseEntity<RepairOutputDto> SetPartRepaired(@PathVariable long id, @RequestBody RepairInputDto repairInputDto) {
        return ResponseEntity.ok(repairService.SetPartRepaired(id, repairInputDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepairOutputDto> updateRepair(@PathVariable Long id, @RequestBody RepairOutputDto repairOutputDto) {
        repairOutputDto.setId(id);
        RepairOutputDto updatedRepair = repairService.updateRepair(id, repairOutputDto);
        if (updatedRepair == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRepair);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRepair(@PathVariable Long id) {
        String message = repairService.deleteRepair(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }


}

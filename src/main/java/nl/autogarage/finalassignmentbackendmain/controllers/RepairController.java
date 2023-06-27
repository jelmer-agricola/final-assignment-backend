package nl.autogarage.finalassignmentbackendmain.controllers;
import jakarta.validation.Valid;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.service.RepairService;
import nl.autogarage.finalassignmentbackendmain.utils.ErrorUtils;
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
    public ResponseEntity <List<RepairOutputDto>>getAllRepair(){
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



}

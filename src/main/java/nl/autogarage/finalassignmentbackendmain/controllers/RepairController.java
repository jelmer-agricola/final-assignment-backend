package nl.autogarage.finalassignmentbackendmain.controllers;
import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.service.RepairService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/repair")
public class RepairController {

    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    ;


    @PostMapping("/add")
    public ResponseEntity<RepairOutputDto> createRepair(@RequestBody RepairInputDto repairInputDto) {
        RepairOutputDto repairOutputDto =repairService.createRepair(repairInputDto);
        return ResponseEntity.ok(repairOutputDto);
    }






}

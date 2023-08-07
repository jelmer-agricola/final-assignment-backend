package nl.autogarage.finalassignmentbackendmain.controllers;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarPartInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.service.CarPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class CarPartController {

    private final CarPartService carPartService;
    public CarPartController(CarPartService carPartService) {
        this.carPartService = carPartService;
    }

    @GetMapping("/license/{licenseplate}")
    public ResponseEntity<Iterable<CarPartOutputDto>> getCarPartsByLicensePlate(@PathVariable String licenseplate) {
        Iterable<CarPartOutputDto> carPartOutputDtos = carPartService.getCarPartsByLicensePlate(licenseplate);
        return ResponseEntity.ok(carPartOutputDtos);
    }

    @GetMapping
    public ResponseEntity<List<CarPartOutputDto>> getAllCarParts() {
        List<CarPartOutputDto> carPartOutputDtos = carPartService.getAllCarParts();
        return ResponseEntity.ok(carPartOutputDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarPartOutputDto> getCarPartById(@PathVariable Long id) {
        CarPartOutputDto carPartOutputDto = carPartService.getCarPartById(id);
        if (carPartOutputDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carPartOutputDto);
    }
    @PutMapping("{licenseplate}/status/{carpart}")
    public ResponseEntity<CarPartOutputDto> CarPartStatusCheck(@PathVariable String licenseplate, @PathVariable String carpart, @RequestBody CarPartInputDto carPartinputDto) {
        return ResponseEntity.ok(carPartService.CarPartStatusCheck(licenseplate, carpart, carPartinputDto));
    }

}





package nl.autogarage.finalassignmentbackendmain.controllers;
import jakarta.validation.Valid;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarPartInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.service.CarPartService;
import nl.autogarage.finalassignmentbackendmain.utils.ErrorUtils;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class CarPartController {

    private final CarPartService carPartService;


    public CarPartController(CarPartService carPartService) {
        this.carPartService = carPartService;
    }

    @GetMapping("/{licenseplate}")
    public ResponseEntity<Iterable<CarPartOutputDto>> getCarPartsByLicensePlate(@PathVariable String licenseplate) {
        Iterable<CarPartOutputDto> carPartOutputDtos = carPartService.getCarPartsByLicensePlate(licenseplate);
        return ResponseEntity.ok(carPartOutputDtos);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createCarPart(@Valid @RequestBody CarPartInputDto carPartInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.errorToStringHandling(bindingResult));
        }
        CarPartOutputDto carPartOutputDto = carPartService.createCarPart(carPartInputDto);
        return ResponseEntity.ok(carPartOutputDto);
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

    @PutMapping("/{id}")
    public ResponseEntity<CarPartOutputDto> updateCarPart(@PathVariable Long id, @RequestBody CarPartInputDto carPartInputDto) {
        CarPartOutputDto updatedCarPart = carPartService.updateCarPart(id, carPartInputDto);
        if (updatedCarPart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCarPart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarPart(@PathVariable Long id) {
        String message = carPartService.deleteCarPart(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }
}





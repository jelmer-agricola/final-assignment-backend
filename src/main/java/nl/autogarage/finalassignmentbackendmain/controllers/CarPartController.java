package nl.autogarage.finalassignmentbackendmain.controllers;


import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.service.CarPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

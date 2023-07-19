package nl.autogarage.finalassignmentbackendmain.controllers;

import jakarta.validation.Valid;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
import nl.autogarage.finalassignmentbackendmain.service.CarService;
import nl.autogarage.finalassignmentbackendmain.utils.ErrorUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("add")
    public ResponseEntity<String> createCar(@Valid @RequestBody CarInputDto carInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.errorToStringHandling(bindingResult));

        } else {
            String createLicensePlate = carService.createCar(carInputDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/car/" + createLicensePlate)
                            .toUriString());

            return ResponseEntity.created(uri).body("Car created. License plate: " + createLicensePlate);
        }
    }



    @GetMapping
    public ResponseEntity<List<CarOutputDto>> getAllCars() {
        return ResponseEntity.ok().body(carService.getAllCars());
    }


    @GetMapping("/licenseplate")
    public ResponseEntity<CarOutputDto> getCarByLicenseplate(@RequestParam String licenseplate) {
        CarOutputDto carOutputDto = carService.getCarByLicenseplate(licenseplate);
        return ResponseEntity.ok(carOutputDto);
    }



//@GetMapping  find owner

    @PutMapping("/licenseplate")
    public ResponseEntity<CarOutputDto> updateCar(@RequestParam String licenseplate, @RequestBody CarOutputDto carOutputDto) {
        return ResponseEntity.ok(carService.updateCar(licenseplate, carOutputDto));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCar(@RequestParam String licenseplate) {
        String message = carService.deleteCar(licenseplate);
        return ResponseEntity.ok(message);
    }

}

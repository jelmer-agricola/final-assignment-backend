package nl.autogarage.finalassignmentbackendmain.controllers;

import jakarta.validation.Valid;
import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
import nl.autogarage.finalassignmentbackendmain.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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


    @PostMapping("/add")
    public ResponseEntity<Object> addCar(@Valid @RequestBody CarInputDto carInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(errorToStringHandling(bindingResult));
        }
        CarOutputDto carOutputDto = carService.addCar(carInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + carOutputDto).toUriString());
        return ResponseEntity.created(uri).body(carOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<CarOutputDto>> getAllCars() {
        return ResponseEntity.ok().body(carService.getAllCars());
    }


    @GetMapping("/licenseplate")
    public ResponseEntity<CarOutputDto> getCarById(@RequestParam String licenseplate) {
        CarOutputDto carOutputDto = carService.getCarByLicenseplate(licenseplate);
        return ResponseEntity.ok(carOutputDto);
    }

//@GetMapping  find owner



    public String errorToStringHandling(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : bindingResult.getFieldErrors()) {
            sb.append(fe.getField() + ": ");
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
        }
        return sb.toString();
    }


}

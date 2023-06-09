package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    //Create
    public CarOutputDto addCar(CarInputDto carInputDto) {
        Optional <Car> optionalCar = carRepository.findById(carInputDto.getLicenseplate());
//        Eeerst een exception schrijven en dan error gooien
        if (optionalCar.isPresent()){
            throw new DuplicateErrorException("Car with license plate already exists");
        }
        Car car = transferInputDtoToCar(carInputDto);
        carRepository.save((car));
        CarOutputDto carOutputDto = transferCarToOutputDto(car);
        return carOutputDto;
    }

    public CarOutputDto getCarByLicenseplate(String licenseplate){
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licenseplate);
        if(optionalCar.isEmpty()){
            throw new RecordNotFoundException("Car with not found with licenseplate" + licenseplate);
        }
            Car car = optionalCar.get();
            CarOutputDto carOUtputDto = transferCarToOutputDto(car);
            return carOUtputDto;
        }

//


    public List<CarOutputDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarOutputDto> carOutputDtos = new ArrayList<>();
        for (Car car : cars) {
            carOutputDtos.add(transferCarToOutputDto(car));
        }
        return carOutputDtos;
    }

    //    Get car by id


//    updateCar

    public CarOutputDto transferCarToOutputDto(Car car) {
        CarOutputDto carOutputDto = new CarOutputDto();


        carOutputDto.setLicenseplate(car.getLicenseplate());
        carOutputDto.setBrand(car.getBrand());

        return carOutputDto;

    }

    public Car transferInputDtoToCar(CarInputDto carInputDto) {
        Car car = new Car();

        car.setLicenseplate(carInputDto.getLicenseplate());
        car.setBrand(carInputDto.getBrand());

        return car;

    }


}


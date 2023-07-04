package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarPartRepository carPartRepository;

    public CarService(CarRepository carRepository, CarPartRepository carPartRepository) {
        this.carRepository = carRepository;
        this.carPartRepository = carPartRepository;
    }


    //Create
    public CarOutputDto createCar (CarInputDto carInputDto) {
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



//    public CarOutputDto createCar(CarInputDto carInputDto) {
//        Optional<Car> optionalCar = carRepository.findById(carInputDto.getLicenseplate());
//        if (optionalCar.isPresent()) {
//            throw new DuplicateErrorException("Car with license plate already exists");
//        }
//
//        Car car = transferInputDtoToCar(carInputDto);
//        carRepository.save(car);
//
//        // Add car parts
//        List<CarPart> carParts = new ArrayList<>();
//        for (CarPartEnum carPartEnum : carInputDto.getCarparts()) {
//            CarPart carPart = new CarPart(carPartEnum, 10); // You can set the desired stock value
//            carPart.setCar(car);
//            carParts.add(carPart);
//        }
//        carPartRepository.saveAll(carParts);
//
//        CarOutputDto carOutputDto = transferCarToOutputDto(car);
//        return carOutputDto;
//    }



    //    Get car by id
    public CarOutputDto getCarByLicenseplate(String licenseplate) {
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licenseplate);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("Car with not found with licenseplate" + licenseplate);
        }
        Car car = optionalCar.get();
        CarOutputDto carOUtputDto = transferCarToOutputDto(car);
        return carOUtputDto;
    }


    public List<CarOutputDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarOutputDto> carOutputDtos = new ArrayList<>();
        for (Car car : cars) {
            carOutputDtos.add(transferCarToOutputDto(car));
        }
        return carOutputDtos;
    }


    //    updateCar
    public CarOutputDto updateCar(String licensePlate, CarOutputDto carOutputDto) {
        Optional<Car> car = carRepository.findByLicenseplate(licensePlate);
        if (car.isEmpty()) {
            throw new RecordNotFoundException("Sorry we did not find a car with license plate: " + licensePlate);
        } else {
            Car updatedCar = car.get();
            updatedCar.setBrand(carOutputDto.getBrand());
            updatedCar.setLicenseplate(carOutputDto.getLicenseplate());

//        updatedCar.set(carOutputDto.getCarStatus());
            carRepository.save(updatedCar);
            return transferCarToOutputDto(updatedCar);
        }
    }


// Record not found exception hieronder werkt. echter return werkt niet

    public String deleteCar(String licensePlate) {
        Optional<Car> car = carRepository.findByLicenseplate(licensePlate);
        if (car.isEmpty()) {
            throw new RecordNotFoundException("Car not found with license plate: " + licensePlate);
        }
        carRepository.delete(car.get());
        return "Car with license plate " + licensePlate + " successfully deleted";
    }


    public CarOutputDto transferCarToOutputDto(Car car) {
        CarOutputDto carOutputDto = new CarOutputDto();


        carOutputDto.setLicenseplate(car.getLicenseplate());
        carOutputDto.setOwner(car.getOwner());
        carOutputDto.setBrand(car.getBrand());
        carOutputDto.setMileage(car.getMileage());

        return carOutputDto;

    }

    public Car transferInputDtoToCar(CarInputDto carInputDto) {
        Car car = new Car();
        car.setOwner(carInputDto.getOwner());
        car.setLicenseplate(carInputDto.getLicenseplate());
        car.setBrand(carInputDto.getBrand());
        car.setMileage(carInputDto.getMileage());
        car.setCarParts(carInputDto.getCarparts());


        return car;

    }


}


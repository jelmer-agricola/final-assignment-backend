package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
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

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarPartRepository carPartRepository;

    public CarService(CarRepository carRepository, CarPartRepository carPartRepository) {
        this.carRepository = carRepository;
        this.carPartRepository = carPartRepository;
    }


    public String createCar(CarInputDto carInputDto) {
        String licenseplate = carInputDto.getLicenseplate();
        Optional<Car> car = carRepository.findByLicenseplate(licenseplate);

        if (car.isPresent()) {
            throw new DuplicateErrorException("Car with license plate already exists");
        }

        Car newcar = transferInputDtoToCar(carInputDto);
        Car savedcar = carRepository.save(newcar);

        CarPartEnum[] carPartEnums = CarPartEnum.values();
        for (CarPartEnum carPartEnum : carPartEnums) {
                CarPart carPart = new CarPart();
                carPart.setCarPartEnum(carPartEnum);
                carPart.setCar(savedcar);
                carPartRepository.save(carPart);
        }
        // TODO: 5-7-2023 paul is awesome 

        savedcar = carRepository.save(savedcar);
        return savedcar.getLicenseplate();
    }


    //    Get car by id
    public CarOutputDto getCarByLicenseplate(String licenseplate) {
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licenseplate);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("Car with not found with licenseplate" + licenseplate);
        }
        Car car = optionalCar.get();
        return transferCarToOutputDto(car);
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
        if (car.getCarParts() != null) {
            carOutputDto.setCarParts(car.getCarParts());
        }

        return carOutputDto;
    }


    public Car transferInputDtoToCar(CarInputDto carInputDto) {
        Car car = new Car();
        car.setOwner(carInputDto.getOwner());
        car.setLicenseplate(carInputDto.getLicenseplate());
        car.setBrand(carInputDto.getBrand());
        car.setMileage(carInputDto.getMileage());
        car.setCarParts(carInputDto.getCarParts());


        return car;

    }


}


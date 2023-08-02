package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarPartInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.*;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarPartService {

    private final CarRepository carRepository;
    private final CarPartRepository carPartRepository;


    public CarPartService(CarRepository carRepository, CarPartRepository carPartRepository) {
        this.carPartRepository = carPartRepository;
        this.carRepository = carRepository;
    }

    public Iterable<CarPartOutputDto> getCarPartsByLicensePlate(String licensePlate) {
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licensePlate);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("No car found with license plate: " + licensePlate);
        }
        Car car = optionalCar.get();

        ArrayList<CarPartOutputDto> carPartOutputDtos = new ArrayList<>();
        for (CarPart carPart : car.getCarParts()) {
            CarPartOutputDto carPartOutputDto = transferCarPartToOutputDto(carPart);
            carPartOutputDtos.add(carPartOutputDto);
        }
        return carPartOutputDtos;
    }
    public List<CarPartOutputDto> getAllCarParts() {
        List<CarPart> carParts = carPartRepository.findAll();
        List<CarPartOutputDto> carPartOutputDtos = new ArrayList<>();
        for (CarPart carPart : carParts) {
            carPartOutputDtos.add(transferCarPartToOutputDto(carPart));
        }
        return carPartOutputDtos;
    }
    public CarPartOutputDto getCarPartById(Long id) {
        Optional<CarPart> optionalCarPart = carPartRepository.findById(id);
        if (optionalCarPart.isPresent()) {
            CarPart carPart = optionalCarPart.get();
            return transferCarPartToOutputDto(carPart);
        } else {
            throw new RecordNotFoundException("CarPart not found with ID " + id);
        }
    }

    public CarPartOutputDto CarPartStatusCheck(String licenseplate, String carpart, CarPartInputDto carPartinputDto) {
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licenseplate);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("No car with license plate: " + licenseplate);
        }

        Car car = optionalCar.get();
        Optional<CarPart> optionalCarPart = car.getCarParts().stream()
                .filter(caPart -> caPart.getCarPartEnum().toString().equals(carpart))
                .findFirst();

        if (optionalCarPart.isEmpty()) {
            throw new RecordNotFoundException("This car part does not exist: " + carpart);
        }

        CarPart carPart = optionalCarPart.get();
        carPart.setPartStatus(carPartinputDto.getPartStatus());
        carPart.setPartIsInspected(carPartinputDto.isPartIsInspected());
        carPart.setCarPartCost(carPartinputDto.getCarPartCost());
        CarPart savedCarPart = carPartRepository.save(carPart);

        return transferCarPartToOutputDto(savedCarPart);
    }



    private CarPartOutputDto transferCarPartToOutputDto(CarPart carPart) {
        CarPartOutputDto carPartOutputDto = new CarPartOutputDto();
        carPartOutputDto.setPartStatus(carPart.getPartStatus());
        carPartOutputDto.setCarPartEnum(carPart.getCarPartEnum());
        carPartOutputDto.setId(carPart.getId());
        carPartOutputDto.setCarPartCost(carPart.getCarPartCost());
        carPartOutputDto.setPartIsInspected(carPart.isPartIsInspected());

        if (carPartOutputDto.getCar() == null) {
            carPartOutputDto.setCar(carPart.getCar());
        }

        return carPartOutputDto;
    }
}


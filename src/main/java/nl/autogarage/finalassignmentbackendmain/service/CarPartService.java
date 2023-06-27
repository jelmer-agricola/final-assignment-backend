package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarPartService {

    private final CarRepository carRepository;
    private final CarPartRepository carPartRepository;

    public CarPartService( CarRepository carRepository, CarPartRepository carPartRepository) {
        this.carPartRepository = carPartRepository;
        this.carRepository = carRepository;
    }

//    moet nog aangepast worden.
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

    private CarPartOutputDto transferCarPartToOutputDto(CarPart carPart) {
        CarPartOutputDto carPartOutputDto = new CarPartOutputDto();
        carPartOutputDto.setInStock(carPart.getInStock());
        carPartOutputDto.setCarPartEnum(carPart.getCarPartEnum());
        return carPartOutputDto;
    }
}


package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List <CarOutputDto> getAllCars(){
        List<Car> cars = carRepository.findAll();
        List<CarOutputDto> carOutputDtos = new ArrayList<>();
        for (Car car : cars){
            carOutputDtos.add(transferCarToOutputDto(car));
        }
        return carOutputDtos;
    }

    public CarOutputDto transferCarToOutputDto(Car car){
        CarOutputDto carOutputDto = new CarOutputDto();


        carOutputDto.setLicenseplate(car.getLicenseplate());

        return carOutputDto;

    }




}


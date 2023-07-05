package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarPartInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
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


    public CarPartOutputDto createCarPart(CarPartInputDto carPartInputDto) {
        CarPart carPart = transferInputDtoToCarPart(carPartInputDto);
        CarPart savedCarPart = carPartRepository.save(carPart);
        return transferCarPartToOutputDto(savedCarPart);
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

    public CarPartOutputDto updateCarPart(Long id, CarPartInputDto carPartInputDto) {
        Optional<CarPart> optionalCarPart = carPartRepository.findById(id);
        if (optionalCarPart.isEmpty()) {
            throw new RecordNotFoundException("No CarPart with id: " + id);
        } else {
            CarPart updatedCarPart = optionalCarPart.get();
            updatedCarPart.setCarPartEnum(carPartInputDto.getCarPartEnum());
            updatedCarPart.setInStock(carPartInputDto.getInStock());
            CarPart savedCarPart = carPartRepository.save(updatedCarPart);
            return transferCarPartToOutputDto(savedCarPart);
        }
    }


    public String deleteCarPart(Long id) {
        if (carPartRepository.existsById(id)) {
            carPartRepository.deleteById(id);
            return "CarPart with ID: " + id + " has been deleted.";
        }
        throw new RecordNotFoundException("CarPart with ID " + id + " does not exist");
    }

    private CarPart transferInputDtoToCarPart(CarPartInputDto carPartInputDto) {
        CarPart carPart = new CarPart();
        carPart.setCarPartEnum(carPartInputDto.getCarPartEnum());
        carPart.setInStock(carPartInputDto.getInStock());
        return carPart;
    }


    private CarPartOutputDto transferCarPartToOutputDto(CarPart carPart) {
        CarPartOutputDto carPartOutputDto = new CarPartOutputDto();
        carPartOutputDto.setInStock(carPart.getInStock());
        carPartOutputDto.setCarPartEnum(carPart.getCarPartEnum());
        return carPartOutputDto;
    }
}


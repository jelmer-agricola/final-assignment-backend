package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarPartInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.*;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CarPartService {

    private final CarRepository carRepository;
    private final CarPartRepository carPartRepository;
    private final RepairService repairService;


    public CarPartService(CarRepository carRepository, CarPartRepository carPartRepository, RepairService repairService) {
        this.carPartRepository = carPartRepository;
        this.carRepository = carRepository;

        this.repairService = repairService;
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


//    public CarPartOutputDto createCarPart(CarPartInputDto carPartInputDto) {
//        CarPart carPart = transferInputDtoToCarPart(carPartInputDto);
//        CarPart savedCarPart = carPartRepository.save(carPart);
//        return transferCarPartToOutputDto(savedCarPart);
//    }

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


//      carparts bijzondere methodes hier te zetten

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
        CarPart savedCarPart = carPartRepository.save(carPart);

        return transferCarPartToOutputDto(savedCarPart);
    }


    private CarPart transferInputDtoToCarPart(CarPartInputDto carPartInputDto) {
        CarPart carPart = new CarPart();
        carPart.setCarPartEnum(carPartInputDto.getCarPartEnum());
        carPart.setPartStatus(carPartInputDto.getPartStatus());
//        carPart.setClientApproved(carPartInputDto.isClientApproved());
        return carPart;
    }


    private CarPartOutputDto transferCarPartToOutputDto(CarPart carPart) {
        CarPartOutputDto carPartOutputDto = new CarPartOutputDto();
        carPartOutputDto.setPartStatus(carPart.getPartStatus());
        carPartOutputDto.setCarPartEnum(carPart.getCarPartEnum());
        carPartOutputDto.setId(carPart.getId());
        carPartOutputDto.setPartIsInspected(carPart.isPartIsInspected());
//        carPartOutputDto.setClientApproved(carPart.isClientApproved());

        if (carPartOutputDto.getCar() == null) {
            carPartOutputDto.setCar(carPart.getCar());
        }

        return carPartOutputDto;
    }
}


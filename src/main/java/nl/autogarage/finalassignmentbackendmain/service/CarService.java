package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.*;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarPartRepository carPartRepository;
    private final InvoiceRepository invoiceRepository;

    public CarService(CarRepository carRepository, CarPartRepository carPartRepository, InvoiceRepository invoiceRepository) {
        this.carRepository = carRepository;
        this.carPartRepository = carPartRepository;
        this.invoiceRepository = invoiceRepository;
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

        return savedcar.getLicenseplate();
    }

    public CarOutputDto getCarByLicenseplate(String licenseplate) {
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licenseplate);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("Car with license  plate " + licenseplate + " not found");
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

    public CarOutputDto updateCar(String licensePlate, CarOutputDto carOutputDto) {
        Optional<Car> car = carRepository.findByLicenseplate(licensePlate);
        if (car.isEmpty()) {
            throw new RecordNotFoundException("Sorry we did not find a car with license plate: " + licensePlate);
        } else {
            Car updatedCar = car.get();
            updatedCar.setBrand(carOutputDto.getBrand());
            updatedCar.setMileage(carOutputDto.getMileage());
            updatedCar.setOwner(carOutputDto.getOwner());
            updatedCar.setLicenseplate(carOutputDto.getLicenseplate());

//        updatedCar.set(carOutputDto.getCarStatus());
            carRepository.save(updatedCar);
            return transferCarToOutputDto(updatedCar);
        }
    }

    public String deleteCar(String licensePlate) {
        Optional<Car> carOptional = carRepository.findByLicenseplate(licensePlate);
        if (carOptional.isEmpty()) {
            throw new RecordNotFoundException("Car not found with license plate: " + licensePlate);
        }
        Car car = carOptional.get();

        List<Inspection> inspections = car.getInspections();
        boolean hasUnfinishedInspection = inspections.stream().anyMatch(inspection -> !inspection.isInspectionFinished());

        List<Invoice> invoices = car.getInvoices();
        boolean isPaid = invoices != null && invoices.stream().anyMatch(Invoice::isPaid);
        if (hasUnfinishedInspection) {
            throw new BadRequestException("Cannot delete car with license plate " + licensePlate + ". There is an unfinished inspection.");
        }
        if (!isPaid) {
            throw new BadRequestException("Cannot delete car with license plate " + licensePlate + ". The corresponding invoice is not paid.");
        }
        carRepository.delete(car);
        return "Car with license plate " + licensePlate + " successfully deleted";
    }

        public CarOutputDto transferCarToOutputDto (Car car){
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
        public Car transferInputDtoToCar (CarInputDto carInputDto){
            Car car = new Car();
            car.setOwner(carInputDto.getOwner());
            car.setLicenseplate(carInputDto.getLicenseplate());
            car.setBrand(carInputDto.getBrand());
            car.setMileage(carInputDto.getMileage());
            car.setCarParts(carInputDto.getCarParts());

            return car;
        }
    }


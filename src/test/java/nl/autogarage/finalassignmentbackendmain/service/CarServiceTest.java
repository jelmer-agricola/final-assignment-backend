package nl.autogarage.finalassignmentbackendmain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarPartInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.quality.Strictness;

@ContextConfiguration(classes = {CarService.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarServiceTest {


    @MockBean
    private InvoiceRepository invoiceRepository;

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarPartRepository carPartRepository;

    @Captor
    private ArgumentCaptor<Car> carCaptor;
    @Captor
    private ArgumentCaptor<CarPart> carPartCaptor;

    @InjectMocks
    private CarService carService;
    private Car car1;
    CarInputDto carInputDto;
    CarOutputDto carOutputDto;
    Car car;
    private CarPart tires;

    private CarPart brakes;
    private CarPartInputDto carPartInputDto1;


    @BeforeEach
    void setUp() {
        carInputDto = new CarInputDto();
        carOutputDto = new CarOutputDto();
        car1 = new Car("33-AAB-3", "TOYOTA", 2500, "Henk de Tank", null, null, null);
//        carRepository.save(car1);
        when(carRepository.save(any(Car.class))).thenReturn(car1);
    }

    @Test
    void testCreateCar() {
        // Arrange
        when(carRepository.findByLicenseplate(anyString())).thenReturn(Optional.empty());
        when(carPartRepository.save(any(CarPart.class))).thenReturn(new CarPart());

        CarInputDto carInputDto = new CarInputDto();
        carInputDto.setBrand("Toyota");
        carInputDto.setCarParts(new ArrayList<>());
        carInputDto.setLicenseplate("33-AAB-3");
        carInputDto.setMileage(100);
        carInputDto.setOwner("henk");

        // Act
        String createdLicensePlate = carService.createCar(carInputDto);
        verify(carRepository, times(1)).save(carCaptor.capture());
        Car capturedCar = carCaptor.getValue();

        // Assert
        assertEquals("33-AAB-3", createdLicensePlate);
        assertEquals("Toyota", capturedCar.getBrand());
        assertEquals(100, capturedCar.getMileage());
        assertEquals("henk", capturedCar.getOwner());

        verify(carRepository).findByLicenseplate(anyString());
        verify(carPartRepository, atLeast(1)).save(any(CarPart.class));
    }

    @Test
    void testCreateCarThrowsDuplicateErrorException() {
        // Arrange
        String licenseplate = "33-AAB-3";

        // Mocking behavior for carRepository.findByLicenseplate
        when(carRepository.findByLicenseplate(licenseplate)).thenReturn(Optional.of(car1));

//        CarInputDto carInputDto = new CarInputDto();
        CarInputDto car2 = new CarInputDto();
        car2.setBrand("Toyota");
        car2.setLicenseplate(licenseplate); // Existing license plate, should throw DuplicateErrorException
        car2.setMileage(2500);
        car2.setOwner("Henk de Tank");

        //        Act & Assert (Paul zegt dat deze hier samen mogen)
        assertThrows(DuplicateErrorException.class, () -> carService.createCar(car2));
        verify(carRepository).findByLicenseplate(licenseplate);
    }


    @Test
    void testGetCarByLicenseplate() {
        // Arrange
        Car car = new Car("33-AAB-3", "TOYOTA", 2500, "Henk de Tank", null, null, null);
        when(carRepository.findByLicenseplate("33-AAB-3")).thenReturn(Optional.of(car));

        // Act
        CarOutputDto actualCarByLicenseplate = carService.getCarByLicenseplate("33-AAB-3");

        // Assert
        assertEquals("TOYOTA", actualCarByLicenseplate.getBrand());
        assertEquals("Henk de Tank", actualCarByLicenseplate.getOwner());
        assertEquals(2500, actualCarByLicenseplate.getMileage());
        assertEquals("33-AAB-3", actualCarByLicenseplate.getLicenseplate());

        verify(carRepository).findByLicenseplate("33-AAB-3");
    }


}
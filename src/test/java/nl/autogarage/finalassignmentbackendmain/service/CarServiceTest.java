package nl.autogarage.finalassignmentbackendmain.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.quality.Strictness;

@ContextConfiguration(classes = {CarService.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarPartRepository carPartRepository;
    @Captor
    private ArgumentCaptor<Car> carCaptor;
    @InjectMocks
    private CarService carService;
    private Car car1;
    CarInputDto carInputDto;
    CarOutputDto carOutputDto;


    @BeforeEach
    void setUp() {
        carInputDto = new CarInputDto();
        carOutputDto = new CarOutputDto();
        car1 = new Car("33-AAB-3", "TOYOTA", 2500, "Henk de Tank", null, null, null);
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

    @Test
    void testGetCarByLicenseplateWhenNotFound() {
        // Arrange
        String licensePlate = "non-existent license plate";
        when(carRepository.findByLicenseplate(licensePlate)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> carService.getCarByLicenseplate(licensePlate));
        verify(carRepository).findByLicenseplate(licensePlate);
    }

    @Test
    void testGetAllCars() {
        // Arrange
        ArrayList<Car> carList = new ArrayList<>();
        ArrayList<CarPart> carParts = new ArrayList<>();
        ArrayList<Inspection> inspectionList = new ArrayList<>();
        Car car = new Car("Licenseplate", "Brand", 1, "Owner", carParts, inspectionList, new ArrayList<>());
        carList.add(car);

        when(carRepository.findAll()).thenReturn(carList);

        // Act
        List<CarOutputDto> actualAllCars = carService.getAllCars();

        // Assert
        assertEquals(1, actualAllCars.size());
        CarOutputDto getResult = actualAllCars.get(0);
        assertEquals(car.getBrand(), getResult.getBrand());
        assertEquals(car.getOwner(), getResult.getOwner());
        assertEquals(car.getMileage().intValue(), getResult.getMileage().intValue());
        assertEquals(car.getLicenseplate(), getResult.getLicenseplate());
        assertEquals(car.getCarParts(), getResult.getCarParts());
        verify(carRepository).findAll();
    }

    @Test
    void testUpdateCar() {
        // Arrange
        String licensePlate = "33-AAB-3";
        Car existingCar = new Car(licensePlate, "TOYOTA", 2500, "Henk de Tank", null, null, null);

        CarOutputDto newCarData = new CarOutputDto();
        newCarData.setBrand("new TOYOTA");
        newCarData.setCarParts(new ArrayList<>());
        newCarData.setLicenseplate(licensePlate);
        newCarData.setMileage(3000);
        newCarData.setOwner("New owner");

        when(carRepository.findByLicenseplate(licensePlate)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CarOutputDto updatedCar = carService.updateCar(licensePlate, newCarData);

        // Assert
        assertEquals(newCarData.getBrand(), updatedCar.getBrand());
        assertEquals(newCarData.getOwner(), updatedCar.getOwner());
        assertEquals(newCarData.getMileage().intValue(), updatedCar.getMileage().intValue());
        assertEquals(newCarData.getLicenseplate(), updatedCar.getLicenseplate());

        verify(carRepository).findByLicenseplate(licensePlate);
        verify(carRepository).save(carCaptor.capture());

        Car savedCar = carCaptor.getValue();
        assertEquals(newCarData.getBrand(), savedCar.getBrand());
        assertEquals(newCarData.getOwner(), savedCar.getOwner());
        assertEquals(newCarData.getMileage().intValue(), savedCar.getMileage());
    }

    @Test
    void testUpdateNonExistingCar() {
        String licensePlate = "AA-BB-12";

        // No car with the provided license plate exists in the repository
        when(carRepository.findByLicenseplate(licensePlate)).thenReturn(Optional.empty());

        CarOutputDto carOutputDto = new CarOutputDto();
        carOutputDto.setBrand("Toyota");
        carOutputDto.setCarParts(new ArrayList<>());
        carOutputDto.setLicenseplate(licensePlate);
        carOutputDto.setMileage(1);
        carOutputDto.setOwner("Jelmer");

        // An attempt to update a non-existing car should result in a RecordNotFoundException
        assertThrows(RecordNotFoundException.class, () -> carService.updateCar(licensePlate, carOutputDto));

        verify(carRepository).findByLicenseplate(licensePlate);
    }

    @Test
    void testDeleteCar_NotFound() {
        // Arrange
        String licensePlate = "AA-BB-12";
        when(carRepository.findByLicenseplate(licensePlate)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> carService.deleteCar(licensePlate));
    }

    @Test
    void testDeleteCar_UnfinishedInspection() {
        // Arrange
        String licensePlate = "AA-BB-12";
        Car car = new Car();
        car.setInspections(Arrays.asList(new Inspection()));
        when(carRepository.findByLicenseplate(licensePlate)).thenReturn(Optional.of(car));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> carService.deleteCar(licensePlate));
    }
    @Test
    void testDeleteCar_UnpaidInvoice() {
        // Arrange
        String licensePlate = "AA-BB-12";
        Car car = new Car();
        car.setInspections(new ArrayList<>());
        Invoice unpaidInvoice = new Invoice();
        unpaidInvoice.setPaid(false);
        car.setInvoices(Arrays.asList(unpaidInvoice));
        when(carRepository.findByLicenseplate(licensePlate)).thenReturn(Optional.of(car));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> carService.deleteCar(licensePlate));
    }

    @Test
    void testDeleteCar_Success() {
        // Arrange
        String licensePlate = "Licenseplate";
        Car car = new Car();
        car.setInspections(new ArrayList<>());
        Invoice paidInvoice = new Invoice();
        paidInvoice.setPaid(true);
        car.setInvoices(Arrays.asList(paidInvoice));
        when(carRepository.findByLicenseplate(licensePlate)).thenReturn(Optional.of(car));

        // Act
        String message = carService.deleteCar(licensePlate);

        // Assert
        verify(carRepository).delete(car);
        assertEquals("Car with license plate " + licensePlate + " successfully deleted", message);
    }

    @Test
    void testTransferInputDtoToCar() {
        // Arrange
        carInputDto.setBrand("TOYOTA");
        carInputDto.setCarParts(new ArrayList<>());
        carInputDto.setLicenseplate("33-AAB-3");
        carInputDto.setMileage(2500);
        carInputDto.setOwner("Henk de Tank");

        // Act
        Car actualTransferInputDtoToCarResult = carService.transferInputDtoToCar(carInputDto);

        // Assert
        assertEquals("TOYOTA", actualTransferInputDtoToCarResult.getBrand());
        assertEquals("Henk de Tank", actualTransferInputDtoToCarResult.getOwner());
        assertEquals(2500, actualTransferInputDtoToCarResult.getMileage().intValue());
        assertEquals("33-AAB-3", actualTransferInputDtoToCarResult.getLicenseplate());
        assertTrue(actualTransferInputDtoToCarResult.getCarParts().isEmpty());
    }

    @Test
    void testTransferCarToOutputDto() {
        // Arrange
        ArrayList<CarPart> carParts = new ArrayList<>();
        car1.setCarParts(carParts);

        // Act
        CarOutputDto actualTransferCarToOutputDtoResult = carService.transferCarToOutputDto(car1);

        // Assert
        assertEquals("TOYOTA", actualTransferCarToOutputDtoResult.getBrand());
        assertEquals("Henk de Tank", actualTransferCarToOutputDtoResult.getOwner());
        assertEquals(2500, actualTransferCarToOutputDtoResult.getMileage().intValue());
        assertEquals("33-AAB-3", actualTransferCarToOutputDtoResult.getLicenseplate());
        assertEquals(carParts, actualTransferCarToOutputDtoResult.getCarParts());
    }
}


package nl.autogarage.finalassignmentbackendmain.integrationtest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.CarPartInputDto;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.CarPartEnum;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import nl.autogarage.finalassignmentbackendmain.service.CarPartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CarPartIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CarPartService carPartService;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarPartRepository carPartRepository;


    Car car1;
    CarPart BATTERIES;
    CarPart BRAKES;
    CarPartInputDto carPartInputDto1;


    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        carPartRepository.deleteAll();
        car1 = new Car("AB-AX-CD", "Toyota", 500, "Jan de Man", null, null, null);
        BATTERIES = new CarPart(120L, "In bad condition", 1010, true, CarPartEnum.BATTERIES, car1, null);
        BRAKES = new CarPart(220L, "In bad condition", 200, true, CarPartEnum.BRAKES, car1, null);

        ArrayList<CarPart> carParts = new ArrayList<>();
        carParts.add(BATTERIES);
        carParts.add(BRAKES);
        car1.setCarParts(carParts);

        carPartInputDto1 = new CarPartInputDto();
        carPartInputDto1.setPartIsInspected(false);
        carPartInputDto1.setPartStatus("In better condition");
        carRepository.save(car1);
    }

    @Test
    void testGetCarPartsByLicensePlate() throws Exception {
        mockMvc.perform(get("/parts/license/" + car1.getLicenseplate()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].partStatus").value("In bad condition"))
                .andExpect(jsonPath("$[0].partIsInspected").value(true));
    }

    @Test
    void testCarPartStatusCheck() throws Exception {
        CarPartInputDto inputDto = new CarPartInputDto();
        inputDto.setPartIsInspected(true);
        inputDto.setPartStatus("In good condition");

        mockMvc.perform(put("/parts/" + car1.getLicenseplate() + "/status/" + BATTERIES.getCarPartEnum().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partStatus").value("In good condition"))
                .andExpect(jsonPath("$.partIsInspected").value(true));
    }

    public static String asJsonString(final CarPartInputDto object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }

    }
}

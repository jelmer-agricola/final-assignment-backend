package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InspectionOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InspectionInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.InspectionRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final CarPartRepository carPartRepository;
    private final CarRepository carRepository;
    private final RepairRepository repairRepository;

    public InspectionService(InspectionRepository inspectionRepository, CarPartRepository carPartRepository, CarRepository carRepository, RepairRepository repairRepository) {
        this.inspectionRepository = inspectionRepository;
        this.carPartRepository = carPartRepository;
        this.carRepository = carRepository;
        this.repairRepository = repairRepository;
    }

//    public InspectionOutputDto createInspection(InspectionInputDto inspectionInputDto) {
//        Inspection inspection = transferInputDtoToInspection(inspectionInputDto);
//        Inspection savedInspection = inspectionRepository.save(inspection);
//        return transferInspectionToOutputDto(savedInspection);
//    }

    public InspectionOutputDto createInspection ( String car_licenseplate){
        Optional<Car> optionalCar = carRepository.findByLicenseplate(car_licenseplate);
        if(optionalCar.isEmpty()){
            throw new RecordNotFoundException("There is no car with license plate " + car_licenseplate);
            }else{
            Car car = optionalCar.get();
            Inspection newInspection = new Inspection();
            newInspection.setCar(car);
//            newInspection.setrepairFinished(false);
//            newInspection.setinspectionApproved(false);
//            newInspection.setMechanic_done(false);
//            newInspection.setCostEstimate(newInspection.getCostEstimate());

            Inspection savedInspection = inspectionRepository.save(newInspection);
            return transferInspectionToOutputDto(savedInspection);

        }
    }


//    /**/


    public List<InspectionOutputDto> getAllInspections() {
        List<Inspection> inspections = inspectionRepository.findAll();
        List<InspectionOutputDto> inspectionOutputDtos = new ArrayList<>();
        for (Inspection inspection : inspections) {
            inspectionOutputDtos.add(transferInspectionToOutputDto(inspection));
        }
        return inspectionOutputDtos;
    }


//    Todo methode om van de inspections de carpart statussen op te halen


    public InspectionOutputDto getInspectionById(Long id) {
        Optional<Inspection> optionalInspection = inspectionRepository.findById(id);
        if (optionalInspection.isPresent()) {
            Inspection inspection = optionalInspection.get();
            return transferInspectionToOutputDto(inspection);
        } else {
            throw new RecordNotFoundException("Inspection not found with ID " + id);
        }
    }

    public InspectionOutputDto updateInspection(Long id, InspectionOutputDto inspectionOutputDto) {
        Optional<Inspection> optionalInspection = inspectionRepository.findById(id);
        if (optionalInspection.isEmpty()) {
            throw new RecordNotFoundException("No insepction with id: " + id);
        } else {
            Inspection updateInspection = optionalInspection.get();
            updateInspection.setInspectionDescription(inspectionOutputDto.getInspectionDescription());
            updateInspection.setCostEstimate(inspectionOutputDto.getCostEstimate());
            updateInspection.setInspectionApproved(inspectionOutputDto.isRepairApproved());
            Inspection savedInspection = inspectionRepository.save(updateInspection);
            return transferInspectionToOutputDto(savedInspection);
        }
    }


    public String deleteInspection(Long id) {
        if (inspectionRepository.existsById(id)) {
            inspectionRepository.deleteById(id);
            return "Inspection with ID: " + id + " has been deleted.";
        }
        throw new RecordNotFoundException("Inspection with ID " + id + " does not exist");
    }

    private Inspection transferInputDtoToInspection(InspectionInputDto inspectionInputDto) {
        Inspection inspection = new Inspection();
        inspection.setCostEstimate(inspectionInputDto.getCostEstimate());
        inspection.setInspectionDescription(inspectionInputDto.getInspectionDescription());
        inspection.setInspectionApproved(inspectionInputDto.isRepairApproved());
        return inspection;
    }

    private InspectionOutputDto transferInspectionToOutputDto(Inspection inspection) {
        InspectionOutputDto inspectionOutputDto = new InspectionOutputDto();
        inspectionOutputDto.setId(inspection.getId());
        inspectionOutputDto.setCostEstimate(inspection.getCostEstimate());
        inspectionOutputDto.setInspectionDescription(inspection.getInspectionDescription());
        inspectionOutputDto.setRepairApproved(inspection.isInspectionApproved());
        return inspectionOutputDto;
    }


}
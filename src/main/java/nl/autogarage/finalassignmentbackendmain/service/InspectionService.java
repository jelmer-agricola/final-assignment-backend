package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InspectionOutputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Repair;
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

    public InspectionOutputDto createInspection(String car_licenseplate) {
        Optional<Car> optionalCar = carRepository.findByLicenseplate(car_licenseplate);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("There is no car with license plate " + car_licenseplate);
        }
        Car car = optionalCar.get();
        boolean isOngoingInspection = inspectionRepository.existsByCarAndInspectionFinished(car, false);
        if (isOngoingInspection) {
            throw new DuplicateErrorException("Cannot create a new inspection. There is an ongoing inspection for the car.");
        }
        Inspection newInspection = new Inspection();
        newInspection.setCar(car);
        newInspection.setInspectionFinished(false);

        Inspection savedInspection = inspectionRepository.save(newInspection);
        return transferInspectionToOutputDto(savedInspection);
    }

    public List<InspectionOutputDto> getAllInspections() {
        List<Inspection> inspections = inspectionRepository.findAll();
        List<InspectionOutputDto> inspectionOutputDtos = new ArrayList<>();
        for (Inspection inspection : inspections) {
            inspectionOutputDtos.add(transferInspectionToOutputDto(inspection));
        }
        return inspectionOutputDtos;
    }

    public InspectionOutputDto getInspectionById(Long id) {
        Optional<Inspection> optionalInspection = inspectionRepository.findById(id);
        if (optionalInspection.isPresent()) {
            Inspection inspection = optionalInspection.get();
            return transferInspectionToOutputDto(inspection);
        } else {
            throw new RecordNotFoundException("Inspection not found with ID " + id);
        }
    }

    public InspectionOutputDto clientApproval(Long id, boolean clientApproved) {
        Optional<Inspection> optionalInspection = inspectionRepository.findById(id);
        if (optionalInspection.isEmpty()) {
            throw new RecordNotFoundException("No inspection found with id: " + id);
        }
        Inspection updateApproval = optionalInspection.get();
        updateApproval.setClientApproved(clientApproved);

        boolean allPartsAreInspected = updateApproval.getRepairs().stream()
                .allMatch(repair -> repair.getCarPart().isPartIsInspected());

        if (clientApproved && !allPartsAreInspected) {
            throw new RecordNotFoundException("Cannot set Client approved to true until all car parts are checked.");
        }

        Inspection savedInspection = inspectionRepository.save(updateApproval);
        return transferInspectionToOutputDto(savedInspection);
    }


    public InspectionOutputDto updateInspection(Long id, InspectionOutputDto inspectionOutputDto) {
        Optional<Inspection> optionalInspection = inspectionRepository.findById(id);
        if (optionalInspection.isEmpty()) {
            throw new RecordNotFoundException("No inspection found with id: " + id);
        }

        Inspection updateInspection = optionalInspection.get();
        updateInspection.setInspectionDescription(inspectionOutputDto.getInspectionDescription());
        boolean allRepairsFinished = updateInspection.getRepairs().stream()
                .allMatch(Repair::isRepairFinished);
        if (allRepairsFinished) {
            updateInspection.setInspectionFinished(inspectionOutputDto.isInspectionFinished());
        } else {
            throw new RecordNotFoundException("Cannot set InspectionFinished to true until all repairs are finished.");
        }

        Inspection savedInspection = inspectionRepository.save(updateInspection);
        return transferInspectionToOutputDto(savedInspection);
    }

    public String deleteInspection(Long id) {
        Inspection inspection = inspectionRepository.findById(id).orElse(null);
        if (inspection != null) {
            if (!inspection.getRepairs().isEmpty()) {
                throw new BadRequestException("Repairs must be deleted before deleting the inspection");
            }

            inspectionRepository.deleteById(id);
            return "Inspection with ID: " + id + " has been deleted.";
        }
        throw new RecordNotFoundException("Inspection with ID " + id + " does not exist");
    }

    public InspectionOutputDto transferInspectionToOutputDto(Inspection inspection) {
        InspectionOutputDto inspectionOutputDto = new InspectionOutputDto();
        inspectionOutputDto.setId(inspection.getId());
        inspectionOutputDto.setInspectionDescription(inspection.getInspectionDescription());
        inspectionOutputDto.setClientApproved(inspection.isClientApproved());
        inspectionOutputDto.setInspectionFinished(inspection.isInspectionFinished());
        inspectionOutputDto.setRepairs(inspection.getRepairs());

        return inspectionOutputDto;
    }
}
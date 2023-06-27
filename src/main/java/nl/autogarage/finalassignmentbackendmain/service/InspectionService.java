package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.InspectionOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InspectionInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.repositories.InspectionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;

    public InspectionService(InspectionRepository inspectionRepository) {
        this.inspectionRepository = inspectionRepository;
    }

    public InspectionOutputDto createInspection(InspectionInputDto inspectionInputDto) {
        Inspection inspection = transferInputDtoToInspection(inspectionInputDto);
        Inspection savedInspection = inspectionRepository.save(inspection);
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

    public InspectionOutputDto updateInspection(Long id, InspectionOutputDto inspectionOutputDto) {
        Optional<Inspection> optionalInspection = inspectionRepository.findById(id);
        if (optionalInspection.isEmpty()) {
            throw new RecordNotFoundException("No insepction with id: " + id);
        } else {
            Inspection updateInspection = optionalInspection.get();
            updateInspection.setDescription(inspectionOutputDto.getDescription());
            updateInspection.setCostEstimate(inspectionOutputDto.getCostEstimate());
            updateInspection.setRepairApproved(inspectionOutputDto.isRepairApproved());
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
        inspection.setDescription(inspectionInputDto.getDescription());
        inspection.setRepairApproved(inspectionInputDto.isRepairApproved());
        return inspection;
    }

    private InspectionOutputDto transferInspectionToOutputDto(Inspection inspection) {
        InspectionOutputDto outputDto = new InspectionOutputDto();
        outputDto.setId(inspection.getId());
        outputDto.setCostEstimate(inspection.getCostEstimate());
        outputDto.setDescription(inspection.getDescription());
        outputDto.setRepairApproved(inspection.isRepairApproved());
        return outputDto;
    }


}
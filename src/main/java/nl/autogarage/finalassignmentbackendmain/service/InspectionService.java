package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.InspectionOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InspectionInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
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
        return transferInspectionListToOutputDtoList(inspections);
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
        Inspection existingInspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Inspection with ID " + id + " does not exist"));

        Inspection updatedInspection = transferOutputDtoToInspection(inspectionOutputDto, existingInspection);
        Inspection savedInspection = inspectionRepository.save(updatedInspection);
        return transferInspectionToOutputDto(savedInspection);
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

    private List<InspectionOutputDto> transferInspectionListToOutputDtoList(List<Inspection> inspections) {
        List<InspectionOutputDto> outputDtoList = new ArrayList<>();
        for (Inspection inspection : inspections) {
            InspectionOutputDto outputDto = transferInspectionToOutputDto(inspection);
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

    private Inspection transferOutputDtoToInspection(InspectionOutputDto outputDto, Inspection inspection) {
        inspection.setCostEstimate(outputDto.getCostEstimate());
        inspection.setDescription(outputDto.getDescription());
        inspection.setRepairApproved(outputDto.isRepairApproved());
        return inspection;
    }
}
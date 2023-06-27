package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.InspectionOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InspectionInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Repair;
import nl.autogarage.finalassignmentbackendmain.repositories.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepairService {

    private final RepairRepository repairRepository;


    public RepairService(RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

    public RepairOutputDto createRepair(RepairInputDto repairInputDto) {
        Repair repair = transferInputDtoToRepair(repairInputDto);
        repairRepository.save(repair);
        RepairOutputDto repairOutputDto = transferRepairToOutputDto(repair);
        return repairOutputDto;
    }





    private Repair transferInputDtoToRepair (RepairInputDto repairInputDto ){
        Repair repair = new Repair();
        repair.setCost(repairInputDto.getCost());
        repair.setId(repairInputDto.getId());
        repair.setDescription(repairInputDto.getDescription());
        repair.setRepairFinished(repairInputDto.isRepairFinished());
        return repair;
    }

    private RepairOutputDto transferRepairToOutputDto (Repair repair){
        RepairOutputDto repairOutputDto = new RepairOutputDto();
        repairOutputDto.setRepairFinished(repair.isRepairFinished());
        repairOutputDto.setCost(repair.getCost());
        repairOutputDto.setDescription(repair.getDescription());
        return repairOutputDto;

    }





}

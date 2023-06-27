package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.CarOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.InspectionOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.OutputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.InspectionInputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.DuplicateErrorException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Repair;
import nl.autogarage.finalassignmentbackendmain.repositories.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        //    in sommig gevallen moet je new repair is saveby repairrepository

        return transferRepairToOutputDto(repair);
    }


    public List <RepairOutputDto> getAllRepair(){
        List<Repair> repairs = repairRepository.findAll();
        List<RepairOutputDto> repairOutputDos = new ArrayList<>();
        for (Repair repair: repairs){
            repairOutputDos.add (transferRepairToOutputDto(repair));
        }
        return repairOutputDos;

    }

    public RepairOutputDto getRepairById(Long id) {
        Optional<Repair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isPresent()) {
            Repair repair = optionalRepair.get();
            return transferRepairToOutputDto(repair);
        } else {
            throw new RecordNotFoundException("Repair not found with ID " + id);
        }
    }





    private Repair transferInputDtoToRepair (RepairInputDto repairInputDto ){
        Repair repair = new Repair();
        repair.setCost(repairInputDto.getCost());
        repair.setDescription(repairInputDto.getDescription());
        repair.setRepairFinished(repairInputDto.isRepairFinished());
        return repair;
    }

    private RepairOutputDto transferRepairToOutputDto (Repair repair){
        RepairOutputDto repairOutputDto = new RepairOutputDto();
        repairOutputDto.setId(repair.getId());
        repairOutputDto.setRepairFinished(repair.isRepairFinished());
        repairOutputDto.setCost(repair.getCost());
        repairOutputDto.setDescription(repair.getDescription());
        return repairOutputDto;

    }





}

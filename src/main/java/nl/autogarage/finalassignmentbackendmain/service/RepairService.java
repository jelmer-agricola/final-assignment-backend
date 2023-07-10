package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.InspectionOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Repair;
import nl.autogarage.finalassignmentbackendmain.repositories.RepairRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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


    //    Todo methode om van de insepections door te geven dat alle carparts weer kloppen.
// Ik wil dus kunnen weten

    public RepairOutputDto updateRepair(Long id, RepairOutputDto repairOutputDto){
        Optional<Repair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isEmpty()){
            throw new RecordNotFoundException("No repair fount with id " + id);
        }else {
            Repair updateRepair = optionalRepair.get();
            updateRepair.setRepairFinished(repairOutputDto.isRepairFinished());
            updateRepair.setFinalCost(repairOutputDto.getFinalCost());
            updateRepair.setRepairDescription(repairOutputDto.getRepairDescription());
            Repair savedRepair = repairRepository.save(updateRepair);
            return transferRepairToOutputDto(savedRepair);
        }

    }



public String deleteRepair(Long id) {
    if (repairRepository.existsById(id)) {
        repairRepository.deleteById(id);
        return "Repair with ID: " + id + " has been deleted.";
    }
    throw new RecordNotFoundException("Repair with ID " + id + " does not exist");
}







    private Repair transferInputDtoToRepair (RepairInputDto repairInputDto ){
        Repair repair = new Repair();
        repair.setFinalCost(repairInputDto.getFinalCost());
        repair.setRepairDescription(repairInputDto.getRepairDescription());
        repair.setRepairFinished(repairInputDto.isRepairFinished());
        return repair;
    }

    private RepairOutputDto transferRepairToOutputDto (Repair repair){
        RepairOutputDto repairOutputDto = new RepairOutputDto();
        repairOutputDto.setId(repair.getId());
        repairOutputDto.setRepairFinished(repair.isRepairFinished());
        repairOutputDto.setFinalCost(repair.getFinalCost());
        repairOutputDto.setRepairDescription(repair.getRepairDescription());
        return repairOutputDto;

    }





}

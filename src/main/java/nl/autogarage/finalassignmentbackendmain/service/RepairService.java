package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.CarPart;
import nl.autogarage.finalassignmentbackendmain.models.Inspection;
import nl.autogarage.finalassignmentbackendmain.models.Repair;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.InspectionRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RepairService {

    private final RepairRepository repairRepository;

    private final InspectionRepository inspectionRepository;
    private final CarPartRepository carPartRepository;
    private final CarRepository carRepository;

    public RepairService(RepairRepository repairRepository, InspectionRepository inspectionRepository, CarPartRepository carPartRepository, CarRepository carRepository) {
        this.repairRepository = repairRepository;
        this.inspectionRepository = inspectionRepository;
        this.carPartRepository = carPartRepository;
        this.carRepository = carRepository;
    }

//    public RepairOutputDto createRepair(RepairInputDto repairInputDto) {
//        Repair repair = transferInputDtoToRepair(repairInputDto);
//        repairRepository.save(repair);
//        //    in sommig gevallen moet je new repair is saveby repairrepository
//
//        return transferRepairToOutputDto(repair);
//    }

    public long createRepair(RepairInputDto repairInputDto, String carPart, long inspection_id) {
        Inspection inspection = inspectionRepository.findById(inspection_id)
                .orElseThrow(() -> new RecordNotFoundException("No inspection found with id: " + inspection_id));
//repair kan alleen aangemaakt worden als in carpart is aangegeven IsInsepcted.

        CarPart carPart1 = new CarPart();
        // Next lines are to get the right carpart by name.
        // This is easier for the mechanic then id for every car has the same basic components.
        for (CarPart carPartx : inspection.getCar().getCarParts()) {
            String carPartEnum = String.valueOf(carPartx.getCarPartEnum());
            if (Objects.equals(carPartEnum, carPart)) {
                carPart1 = carPartRepository.findById(carPartx.getId())
                        .orElseThrow(() -> new RecordNotFoundException("No carpart found to repair"));
            }
        }
        Repair newrepair = transferInputDtoToRepair(repairInputDto);
        newrepair.setCarpart(carPart1);
        newrepair.setInspection(inspection);
        Repair savedrepair = repairRepository.save(newrepair);
        return savedrepair.getId();
    }





    public List <RepairOutputDto> getAllRepair(){
        List<Repair> repairs = repairRepository.findAll();
        List<RepairOutputDto> repairOutputDos = new ArrayList<>();
        for (Repair repair: repairs){
            repairOutputDos.add (transferRepairToOutputDto(repair));
        }
        return repairOutputDos;

    }
    public Iterable<RepairOutputDto> getAllRepairsFromLicenceplate(String licenseplate) {
        Optional<Car> optionalCar = carRepository.findByLicenseplate(licenseplate);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("No car found with license plate: " + licenseplate);
        }
        Car car = optionalCar.get();

        ArrayList<RepairOutputDto> repairOutputDtos = new ArrayList<>();
        List<Inspection> inspections = car.getInspections();
        if (inspections.size() > 0) {
            Inspection lastInspection = inspections.get(inspections.size() - 1);
            for (Repair repair : lastInspection.getRepairs()) {
                RepairOutputDto repairOutputDto = transferRepairToOutputDto(repair);
                repairOutputDtos.add(repairOutputDto);
            }
        } else {
            throw new RecordNotFoundException("No repairs found for this car");
        }
        return repairOutputDtos;
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
//klopt het met insepctionapproved ??? of gaat het juist om een repair? ?
    public RepairOutputDto SetPartRepaired (long id, RepairInputDto repairInputDto){
        Repair repair = repairRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException("No Repair found with id: " + id));
        if (!repair.getInspection().isInspectionApproved()){
            throw new RecordNotFoundException("The customer has not approved of the Inspection yet");
        }else{
            repair.setRepairFinished(repairInputDto.isRepairFinished());
            repairRepository.save(repair);
            return transferRepairToOutputDto(repair);
        }
    }



    //    Todo methode om van de insepections door te geven dat alle carparts weer kloppen.

    public RepairOutputDto updateRepair(Long id, RepairOutputDto repairOutputDto){
        Optional<Repair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isEmpty()){
            throw new RecordNotFoundException("No repair fount with id " + id);
        }else {
            Repair updateRepair = optionalRepair.get();
            updateRepair.setRepairFinished(repairOutputDto.isRepairFinished());
            updateRepair.setPartRepairCost(repairOutputDto.getPartRepairCost());
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
        repair.setPartRepairCost(repairInputDto.getPartRepairCost());
        repair.setRepairDescription(repairInputDto.getRepairDescription());
        repair.setRepairFinished(repairInputDto.isRepairFinished());
        repair.setCarpart(repairInputDto.getCarPart());
        repair.setInspection(repairInputDto.getInspection());
        return repair;
    }

    private RepairOutputDto transferRepairToOutputDto (Repair repair){
        RepairOutputDto repairOutputDto = new RepairOutputDto();
        repairOutputDto.setId(repair.getId());
        repairOutputDto.setRepairFinished(repair.isRepairFinished());
        repairOutputDto.setPartRepairCost(repair.getPartRepairCost());
        repairOutputDto.setRepairDescription(repair.getRepairDescription());
        repairOutputDto.setCarPart(repair.getCarpart());
        repairOutputDto.setInspection(repair.getInspection());
        return repairOutputDto;

    }





}

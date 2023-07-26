package nl.autogarage.finalassignmentbackendmain.service;


import nl.autogarage.finalassignmentbackendmain.dto.outputDto.CarPartOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.outputDto.RepairOutputDto;
import nl.autogarage.finalassignmentbackendmain.dto.inputDto.RepairInputDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.*;
import nl.autogarage.finalassignmentbackendmain.repositories.CarPartRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.CarRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.InspectionRepository;
import nl.autogarage.finalassignmentbackendmain.repositories.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public long createRepair(RepairInputDto repairInputDto, String carPart, long inspection_id) {
        Inspection inspection = inspectionRepository.findById(inspection_id)
                .orElseThrow(() -> new RecordNotFoundException("No inspection found with id: " + inspection_id));

        boolean repairExists = inspection.getRepairs().stream()
                .anyMatch(repair -> repair.getCarPart().getCarPartEnum().toString().equalsIgnoreCase(carPart));

        if (repairExists) {
            throw new BadRequestException("A repair for the car part '" + carPart + "' already exists in this inspection.");
        }
        CarPart carPart1 = new CarPart();
        for (CarPart carPartx : inspection.getCar().getCarParts()) {
            String carPartEnum = String.valueOf(carPartx.getCarPartEnum());
            if (Objects.equals(carPartEnum, carPart)) {
                carPart1 = carPartRepository.findById(carPartx.getId())
                        .orElseThrow(() -> new RecordNotFoundException("No car part found to repair"));
            }
        }
        Repair newrepair = transferInputDtoToRepair(repairInputDto);
        newrepair.setCarPart(carPart1);
        newrepair.setInspection(inspection);
        Repair savedrepair = repairRepository.save(newrepair);
        return savedrepair.getId();
    }

    public Map<Long, CarPartEnum> createRepairsForAllCarPartsInInspection(long inspection_id) {
        Inspection inspection = inspectionRepository.findById(inspection_id)
                .orElseThrow(() -> new RecordNotFoundException("No inspection found with id: " + inspection_id));

        Map<Long, CarPartEnum> savedRepairIdsAndCarPartEnums = new HashMap<>();

        boolean repairsExistForAllCarParts = Arrays.stream(CarPartEnum.values())
                .allMatch(carPartEnum -> inspection.getRepairs().stream()
                        .anyMatch(repair -> repair.getCarPart().getCarPartEnum() == carPartEnum));

        if (repairsExistForAllCarParts) {
            throw new BadRequestException("The repairs are already assigned to an inspection");
        }

        for (CarPartEnum carPartEnum : CarPartEnum.values()) {
            // Check if a repair for the current car part already exists in the inspection
            boolean repairExists = inspection.getRepairs().stream()
                    .anyMatch(repair -> repair.getCarPart().getCarPartEnum() == carPartEnum);

            if (!repairExists) {
                CarPart carPart = inspection.getCar().getCarParts()
                        .stream()
                        .filter(part -> part.getCarPartEnum() == carPartEnum)
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("No car part found to repair"));

                Repair newRepair = new Repair();
                newRepair.setCarPart(carPart);
                newRepair.setInspection(inspection);
                // Set other properties for the new repair based on the input or default values

                Repair savedRepair = repairRepository.save(newRepair);
                savedRepairIdsAndCarPartEnums.put(savedRepair.getId(), carPartEnum);
            }
        }

        return savedRepairIdsAndCarPartEnums;
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

    public RepairOutputDto SetPartRepaired (long id, RepairInputDto repairInputDto){
        Repair repair = repairRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException("No Repair found with id: " + id));

        if (!repair.getCarPart().isPartIsInspected()) {
//            Bad requeest
            throw new RecordNotFoundException("The part must be inspected before it can be marked as repaired.");
        }else{
            repair.setRepairFinished(repairInputDto.isRepairFinished());
            repair.setRepairDescription(repairInputDto.getRepairDescription());
            repairRepository.save(repair);
            return transferRepairToOutputDto(repair);
        }
    }


    public RepairOutputDto updateRepair(Long id, RepairOutputDto repairOutputDto){
        Optional<Repair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isEmpty()){
            throw new RecordNotFoundException("No repair fount with id " + id);
        }else {
            Repair updateRepair = optionalRepair.get();
            updateRepair.setRepairFinished(repairOutputDto.isRepairFinished());
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
        repair.setRepairDescription(repairInputDto.getRepairDescription());
        repair.setRepairFinished(repairInputDto.isRepairFinished());
        repair.setCarPart(repairInputDto.getCarPart());
        repair.setInspection(repairInputDto.getInspection());
        return repair;
    }

    private RepairOutputDto transferRepairToOutputDto (Repair repair){
        RepairOutputDto repairOutputDto = new RepairOutputDto();
        repairOutputDto.setId(repair.getId());
        repairOutputDto.setRepairFinished(repair.isRepairFinished());
        repairOutputDto.setRepairDescription(repair.getRepairDescription());
        repairOutputDto.setCarPart(repair.getCarPart());
        repairOutputDto.setInspection(repair.getInspection());

        return repairOutputDto;

    }





}

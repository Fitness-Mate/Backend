package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineDto;
import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineRequest;
import FitMate.FitMateBackend.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.repository.MachineRepository;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.workout.entity.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final BodyPartService bodyPartService;

    @Transactional
    public ResponseEntity<String> saveMachine(MachineRequest request) {
        if(!this.checkMachineNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            throw new CustomException(CustomErrorCode.MACHINE_ALREADY_EXIST_EXCEPTION);

        Machine machine = new Machine();
        machine.update(request.getEnglishName(), request.getKoreanName());

        for (String koreanName : request.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(koreanName);
            findBodyPart.addMachine(machine);
            machine.getBodyParts().add(findBodyPart);
        }

        machineRepository.save(machine);
        return ResponseEntity.ok("[" + machine.getKoreanName() + ":" + machine.getEnglishName() + "] 등록 완료");
    }

    @Transactional
    public ResponseEntity<String> updateMachine(Long machineId, MachineRequest request) {
        if(!this.checkMachineNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            throw new CustomException(CustomErrorCode.MACHINE_ALREADY_EXIST_EXCEPTION);

        Machine findMachine = machineRepository.findById(machineId).orElse(null);
        if(findMachine == null)
            throw new CustomException(CustomErrorCode.MACHINE_NOT_FOUND_EXCEPTION);

        findMachine.update(request.getEnglishName(), request.getKoreanName());

        for (BodyPart bodyPart : findMachine.getBodyParts()) {
            bodyPart.removeMachine(findMachine);
        }
        findMachine.getBodyParts().clear();

        for (String name : request.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(name);
            findBodyPart.addMachine(findMachine);
            findMachine.getBodyParts().add(findBodyPart);
        }

        return ResponseEntity.ok("[" + findMachine.getKoreanName() + ":" + findMachine.getEnglishName() + "] 수정 완료");
    }

    //Overloading
    public List<Machine> findAll() {
        return machineRepository.findAll();
    }
    public ResponseEntity<?> findAll(int page) {
        List<Machine> findMachines = machineRepository.findAll(page);
        if(findMachines.isEmpty())
            throw new CustomException(CustomErrorCode.PAGE_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(
                findMachines.stream()
                .map(MachineDto::new)
                .collect(Collectors.toList()));
    }
    //Overloading

    public Machine findOne(Long machineId) {
        return machineRepository.findById(machineId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MACHINE_NOT_FOUND_EXCEPTION));
    }

    public boolean checkMachineNameDuplicate(String koreanName, String englishName) {
        Machine m1 = machineRepository.findByKoreanName(koreanName).orElse(null);
        Machine m2 = machineRepository.findByEnglishName(englishName).orElse(null);
        return (m1 == null && m2 == null);
    }

    public List<Machine> findWithBodyPart(List<String> bodyPartKoreanName) {
        if(bodyPartKoreanName == null) return null;
        return machineRepository.findWithBodyPart(bodyPartKoreanName);
    }

    @Transactional
    public ResponseEntity<String> removeMachine(Long machineId) {
        Machine findMachine = machineRepository.findById(machineId).orElse(null);
        if(findMachine == null)
            throw new CustomException(CustomErrorCode.MACHINE_NOT_FOUND_EXCEPTION);

        //machine과 관련된 운동부위 삭제
        for (BodyPart bodyPart : findMachine.getBodyParts()) {
            bodyPart.removeMachine(findMachine);
        }
        //machine과 관련된 운동 삭제
        for (Workout workout : findMachine.getWorkouts()) {
            workout.removeMachine(findMachine);
        }

        machineRepository.remove(findMachine);
        return ResponseEntity.ok("[" + findMachine.getKoreanName() + ":" + findMachine.getEnglishName() + "] 삭제 완료");
    }
}

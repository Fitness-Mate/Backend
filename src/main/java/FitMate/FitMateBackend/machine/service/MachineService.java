package FitMate.FitMateBackend.machine.service;

import FitMate.FitMateBackend.machine.dto.MachineDto;
import FitMate.FitMateBackend.machine.repository.MachineRepository;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.machine.entity.Machine;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;

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

    public List<Machine> findWithBodyPart(List<String> bodyPartKoreanName) {
        if(bodyPartKoreanName == null) return null;
        return machineRepository.findWithBodyPart(bodyPartKoreanName);
    }
}

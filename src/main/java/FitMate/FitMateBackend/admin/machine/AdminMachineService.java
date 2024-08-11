package FitMate.FitMateBackend.admin.machine;

import FitMate.FitMateBackend.admin.bodypart.AdminBodyPartRepository;
import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineRequest;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.exception.ApiErrorCode;
import FitMate.FitMateBackend.exception.ApiException;
import FitMate.FitMateBackend.machine.dto.MachineResponse;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.machine.entity.MachineMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminMachineService {

    private final AdminMachineRepository adminMachineRepository;
    private final AdminBodyPartRepository adminBodyPartRepository;

    @Transactional
    public void create(MachineRequest request) {
        Optional<Machine> duplicateMachine = adminMachineRepository.findByKoreanNameOrEnglishName(
            request.getKoreanName(),
            request.getEnglishName()
        );
        if(duplicateMachine.isPresent()) {
            throw new ApiException(ApiErrorCode.MACHINE_ALREADY_EXIST_EXCEPTION);
        }

        Machine machine = MachineMapper.toEntity(request);

        for (Long bodyPartId : request.getBodyPartIdList()) {
            BodyPart bodyPart = adminBodyPartRepository.findById(bodyPartId).orElseThrow(
                () -> new ApiException(ApiErrorCode.BODY_PART_NOT_FOUND_EXCEPTION)
            );

            bodyPart.addMachine(machine);
            machine.addBodyPart(bodyPart);
        }

        adminMachineRepository.save(machine);
    }


    public PageImpl<MachineResponse> readAll(Pageable pageable) {
        Page<Machine> machineList = adminMachineRepository.findAll(pageable);
        return new PageImpl<>(
            machineList.stream().map(MachineResponse::new).toList(),
            pageable,
            machineList.getTotalElements()
        );
    }
}

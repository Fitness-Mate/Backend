package FitMate.FitMateBackend.admin.machine;

import FitMate.FitMateBackend.admin.bodypart.AdminBodyPartRepository;
import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.exception.ApiErrorCode;
import FitMate.FitMateBackend.common.exception.ApiException;
import FitMate.FitMateBackend.machine.dto.MachineRequest;
import FitMate.FitMateBackend.machine.dto.MachineResponse;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.machine.entity.MachineMapper;
import FitMate.FitMateBackend.workout.entity.Workout;
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

				// MultipartFile에서 파일명 추출
				String imgFileName = request.getImage().getOriginalFilename();
        Machine machine = MachineMapper.toEntity(request, imgFileName);

        for (Long bodyPartId : request.getBodyPartIdList()) {
            BodyPart bodyPart = adminBodyPartRepository.findById(bodyPartId).orElseThrow(
                () -> new ApiException(ApiErrorCode.BODY_PART_NOT_FOUND_EXCEPTION)
            );

            bodyPart.addMachine(machine);
            machine.addBodyPart(bodyPart);
        }

        adminMachineRepository.save(machine);
    }

    @Transactional
    public void update(Long machineId, MachineRequest request) {
//        if(!this.checkMachineNameDuplicate(request.getKoreanName(), request.getEnglishName()))
//            throw new CustomException(CustomErrorCode.MACHINE_ALREADY_EXIST_EXCEPTION);
//
//        Machine findMachine = machineRepository.findById(machineId).orElse(null);
//        if(findMachine == null)
//            throw new CustomException(CustomErrorCode.MACHINE_NOT_FOUND_EXCEPTION);
//
//        findMachine.update(request.getEnglishName(), request.getKoreanName());
//
//        for (BodyPart bodyPart : findMachine.getBodyParts()) {
//            bodyPart.removeMachine(findMachine);
//        }
//        findMachine.getBodyParts().clear();
//
//        for (String name : request.getBodyPartKoreanName()) {
//            BodyPart findBodyPart = bodyPartService.findByKoreanName(name);
//            findBodyPart.addMachine(findMachine);
//            findMachine.getBodyParts().add(findBodyPart);
//        }
    }

    public PageImpl<MachineResponse> readAll(Pageable pageable) {
        Page<Machine> machineList = adminMachineRepository.findAll(pageable);
        return new PageImpl<>(
            machineList.stream().map(MachineResponse::new).toList(),
            pageable,
            machineList.getTotalElements()
        );
    }

    @Transactional
    public void delete(Long id) {
        Machine machine = adminMachineRepository.findById(id).orElseThrow(
            () -> new ApiException(ApiErrorCode.MACHINE_NOT_FOUND_EXCEPTION)
        );

        for (Workout workout : machine.getWorkouts()) workout.removeMachine(machine);
        for (BodyPart bodyPart : machine.getBodyParts()) bodyPart.removeMachine(machine);

        adminMachineRepository.deleteById(id);
    }
}

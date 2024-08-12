package FitMate.FitMateBackend.workout.service;

import FitMate.FitMateBackend.workout.dto.WorkoutSearchCond;
import FitMate.FitMateBackend.bodypart.repository.BodyPartRepository;
import FitMate.FitMateBackend.machine.repository.MachineRepository;
import FitMate.FitMateBackend.bodypart.service.BodyPartService;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.common.util.S3Util;
import FitMate.FitMateBackend.workout.dto.WorkoutRequest;
import FitMate.FitMateBackend.workout.dto.WorkoutResponse;
import FitMate.FitMateBackend.workout.entity.Workout;
import FitMate.FitMateBackend.workout.repository.WorkoutRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final MachineRepository machineRepository;
    private final BodyPartRepository bodyPartRepository;
    private final BodyPartService bodyPartService;
    private final S3Util s3Util;

    @Override
    @Transactional
    public WorkoutResponse update(WorkoutRequest form, Long id) {
        if(!this.checkWorkoutNameDuplicate(form.getKoreanName(), form.getEnglishName()))
            throw new CustomException(CustomErrorCode.WORKOUT_ALREADY_EXIST_EXCEPTION);

        Workout findWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION));

        if(!findWorkout.getImgFileName().equals(ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME)) //기존 이미지 삭제
            s3Util.deleteImage(ServiceConst.S3_DIR_WORKOUT,findWorkout.getImgFileName());

        if(!form.getImage().isEmpty()) {
            String fileName = s3Util.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage()); //s3에 이미지 추가
            findWorkout.update(form, fileName);
        } else {
            findWorkout.update(form, ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME);
        }

        //workout <-> bodyPart 연결 해제
        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }
        findWorkout.getBodyParts().clear();

        //workout과 연관된 bodyPart 연결
        for (Long bodyPartId : form.getBodyPartIdList()) {
            BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId)
                .orElseThrow(()->new CustomException(CustomErrorCode.BODY_PART_NOT_FOUND_EXCEPTION));
            findWorkout.getBodyParts().add(findBodyPart);
            findBodyPart.addWorkout(findWorkout);
        }

        //workout <-> machine 연결 해제
        for (Machine machine : findWorkout.getMachines()) {
            machine.removeWorkout(findWorkout);
        }
        findWorkout.getMachines().clear();

        //workout과 연관된 machine 연결
        for (Long machineId : form.getMachineIdList()) {
            Machine findMachine = machineRepository.findById(machineId)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.MACHINE_NOT_FOUND_EXCEPTION));
            findWorkout.getMachines().add(findMachine);
            findMachine.addWorkout(findWorkout);
        }

        return new WorkoutResponse(findWorkout);
    }

    @Override
    public WorkoutResponse findById(Long id) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION));
        return new WorkoutResponse(workout);
    }

    public boolean checkWorkoutNameDuplicate(String koreanName, String englishName) {
        Workout w1 = workoutRepository.findByKoreanName(koreanName).orElse(null);
        Workout w2 = workoutRepository.findByEnglishName(englishName).orElse(null);
        return (w1 == null && w2 == null);
    }

    public ResponseEntity<?> findAll(int page) {
        List<Workout> findWorkouts = workoutRepository.findAll(page);
        if(findWorkouts.isEmpty())
            throw new CustomException(CustomErrorCode.PAGE_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(
                findWorkouts.stream()
                .map(WorkoutResponse::new)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public WorkoutResponse remove(Long id) {
        Workout findWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION));

        //workout과 연관된 bodyPart제거
        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }
        //workout과 연관된 machine제거
        for (Machine machine : findWorkout.getMachines()) {
            machine.removeWorkout(findWorkout);
        }
        if(findWorkout.getImgFileName() != null) {
            //workout 이미지 삭제
            if(!findWorkout.getImgFileName().equals(ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME))
                s3Util.deleteImage(ServiceConst.S3_DIR_WORKOUT, findWorkout.getImgFileName());
        }

        workoutRepository.remove(findWorkout);
        return new WorkoutResponse(findWorkout);
    }

    @Override
    public List<WorkoutResponse> searchAll(int page, WorkoutSearchCond cond) {
        return workoutRepository.searchAll(page, cond)
            .stream().map(WorkoutResponse::new).toList();
    }

    public String getAllWorkoutToString(List<BodyPart> bodyParts, List<Machine> machines) {
        List<Workout> workouts = workoutRepository.findAllWithBodyPartsAndMachines(bodyParts, machines);

        String str = "list: [ ";
        for (int i = 0; i < workouts.size(); i++) {

            if(i == (workouts.size()-1)) str += " " + workouts.get(i).getEnglishName() + "(" + workouts.get(i).getId() + ")";
            else str += workouts.get(i).getEnglishName() + "(" + workouts.get(i).getId() + "), ";
        }
        str += " ]";

        return str;
    }
}
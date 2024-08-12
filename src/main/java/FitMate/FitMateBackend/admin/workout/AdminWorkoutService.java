package FitMate.FitMateBackend.admin.workout;

import FitMate.FitMateBackend.admin.bodypart.AdminBodyPartRepository;
import FitMate.FitMateBackend.admin.machine.AdminMachineRepository;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.exception.ApiErrorCode;
import FitMate.FitMateBackend.common.exception.ApiException;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.util.S3Util;
import FitMate.FitMateBackend.workout.dto.WorkoutRequest;
import FitMate.FitMateBackend.workout.dto.WorkoutResponse;
import FitMate.FitMateBackend.workout.entity.Workout;
import FitMate.FitMateBackend.workout.entity.WorkoutMapper;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j(topic = "AdminWorkoutService")
public class AdminWorkoutService {

    private final AdminWorkoutRepository adminWorkoutRepository;
    private final AdminWorkoutQueryRepository adminWorkoutQueryRepository;

    private final AdminBodyPartRepository adminBodyPartRepository;
    private final AdminMachineRepository adminMachineRepository;

    private final S3Util s3Util;

    /**
     * Workout 생성
     *
     * @param request Workout 생성 정보
     */
    @Transactional
    public void create(WorkoutRequest request) {

        /* 운동 이름 중복 예외처리 */
        Optional<Workout> duplicateWorkout = adminWorkoutRepository.findByKoreanNameOrEnglishName(
            request.getKoreanName(),
            request.getEnglishName()
        );
        if (duplicateWorkout.isPresent()) {
            throw new ApiException(ApiErrorCode.WORKOUT_ALREADY_EXIST_EXCEPTION);
        }

        /* S3 파일 업로드 */
        String imageName = Objects.isNull(request.getImage())
            ? ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME
            : s3Util.uploadImage(ServiceConst.S3_DIR_WORKOUT, request.getImage());

        Workout workout = WorkoutMapper.toEntity(request, imageName);

        /* workout - bodypart 연결*/
//        List<BodyPart> bodyParts = workout.getBodyParts();
        for (Long bodyPartId : request.getBodyPartIdList()) {
            BodyPart bodyPart = adminBodyPartRepository.findById(bodyPartId).orElseThrow(
                () -> new ApiException(ApiErrorCode.BODY_PART_NOT_FOUND_EXCEPTION)
            );

            workout.addBodypart(bodyPart);
            bodyPart.addWorkout(workout);
        }

        /* workout - machine 연결 */
        for (Long machineId : request.getMachineIdList()) {
            Machine machine = adminMachineRepository.findById(machineId).orElseThrow(
                () -> new ApiException(ApiErrorCode.MACHINE_NOT_FOUND_EXCEPTION)
            );

            workout.addMachine(machine);
            machine.addWorkout(workout);
        }

        adminWorkoutRepository.save(workout);
    }

    /**
     * Workout 조회
     *
     * @param id 조회 대상 Workout
     */
    public WorkoutResponse read(Long id) {
        Workout workout = adminWorkoutRepository.findById(id).orElseThrow(
            () -> new ApiException(ApiErrorCode.WORKOUT_NOT_FOUND_EXCEPTION)
        );

        return new WorkoutResponse(workout);
    }

    /**
     * 운동 리스트 조회
     *
     * @param pageable 페이징 정보
     */
    public PageImpl<WorkoutResponse> readAll(Pageable pageable) {
        Page<Workout> workoutList = adminWorkoutRepository.findAll(pageable);
        return new PageImpl<>(
            workoutList.stream().map(WorkoutResponse::new).toList(),
            pageable,
            workoutList.getTotalElements()
        );
    }

    /**
     * Workout 업데이트 (개발 중)
     *
     * @param request Workout 업데이트 정보
     * @param id      업데이트 대상 Workout
     */
    @Transactional
    public void update(WorkoutRequest request, Long id) {
        Workout workout = adminWorkoutRepository.findById(id).orElseThrow(
            () -> new ApiException(ApiErrorCode.WORKOUT_NOT_FOUND_EXCEPTION)
        );

        /* 운동 이름 중복 예외처리 */
        Optional<Workout> duplicateWorkout = adminWorkoutRepository.findByKoreanNameOrEnglishName(
            request.getKoreanName(), request.getEnglishName());
        if (duplicateWorkout.isPresent()) {
            throw new ApiException(ApiErrorCode.WORKOUT_ALREADY_EXIST_EXCEPTION);
        }

    }

    /**
     * Workout 삭제
     *
     * @param id 삭제 대상 Workout
     */
    @Transactional
    public void delete(Long id) {
        adminWorkoutRepository.findById(id).orElseThrow(
            () -> new ApiException(ApiErrorCode.WORKOUT_NOT_FOUND_EXCEPTION)
        );
        adminWorkoutRepository.deleteById(id);
    }
}

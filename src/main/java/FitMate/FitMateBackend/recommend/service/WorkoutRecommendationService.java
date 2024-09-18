package FitMate.FitMateBackend.recommend.service;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.bodypart.repository.BodyPartRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.machine.repository.MachineRepository;
import FitMate.FitMateBackend.recommend.entity.RecommendedWorkout;
import FitMate.FitMateBackend.recommend.entity.WorkoutRecommendation;
import FitMate.FitMateBackend.recommend.repository.RecommendedWorkoutRepository;
import FitMate.FitMateBackend.recommend.repository.WorkoutRecommendationRepository;
import FitMate.FitMateBackend.user.entity.User;
import FitMate.FitMateBackend.workout.dto.WorkoutRecommendationRequest;
import FitMate.FitMateBackend.workout.entity.Workout;
import FitMate.FitMateBackend.workout.repository.WorkoutRepository;
import FitMate.FitMateBackend.workout.service.WorkoutServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutRecommendationService {

    private final UserRepository userRepository;
    private final WorkoutRecommendationRepository workoutRecommendationRepository;
    private final BodyPartRepository bodyPartRepository;
    private final MachineRepository machineRepository;
    private final WorkoutServiceImpl workoutServiceImpl;
    private final WorkoutRepository workoutRepository;
    private final RecommendedWorkoutRepository recommendedWorkoutRepository;
		private static final Logger logger = LoggerFactory.getLogger(WorkoutRecommendationService.class);

    @Transactional
    public Long createWorkoutRecommendation(Long userId, WorkoutRecommendationRequest request) {
        User user = userRepository.findOne(userId);

        List<BodyPart> bodyParts = bodyPartRepository.findByBodyPartKoreanName(request.getBodyPartKoreanName());
        List<Machine> machines = machineRepository.findByMachineKoreanName(request.getMachineKoreanName());

        WorkoutRecommendation workoutRecommendation =
                WorkoutRecommendation.createWorkoutRecommendation
                        (user, bodyParts, machines, workoutServiceImpl.getAllWorkoutToString(bodyParts, machines));

        workoutRecommendationRepository.save(workoutRecommendation);
        user.addRecommendationHistory(workoutRecommendation);
        return workoutRecommendation.getId();
    }

    @Transactional(readOnly = false) // 쓰기 작업에 대해 트랜잭션 설정
public void updateResponse(Long recommendationId, String response) throws Exception {
	WorkoutRecommendation workoutRecommendation = workoutRecommendationRepository.findById(recommendationId);

	// response를 로그로 출력하여 응답 형식을 확인
        logger.debug("GPT Response: {}", response);

if (workoutRecommendation == null) {
    throw new IllegalArgumentException("Recommendation not found");
}

    if (workoutRecommendation.getRequestedBodyParts() == null) {
    throw new IllegalArgumentException("Requested body parts cannot be null.");
}


    String[] sentences = response.split("\n");

    Set<String> uniqueResponses = new HashSet<>();

    for (String sentence : sentences) {
    if (!sentence.matches("\\[\\d+\\]\\s*\\[\\d+\\s*kg\\]\\s*\\[\\d+\\]\\s*\\[\\d+\\]\\s*(\\[.*\\])?")) {
        logger.error("Invalid response format: {}", sentence);
        continue;
    }

    if (!uniqueResponses.add(sentence)) {
        logger.info("Duplicate response detected and skipped: {}", sentence);
        continue;
    }

    try {
        String[] info = sentence.split("\\]\\s*\\[");
        long workoutId = Long.parseLong(info[0].replace("[", "").trim());
        String weight = info[1].replace("kg", "").trim();
        String repeat = info[2].trim();
        String set = info[3].replace("]", "").trim();
        String caution = (info.length > 4) ? info[4].replace("]", "").trim() : "주의사항이 없습니다.";

        // 중복 체크: 같은 추천 ID와 운동 ID가 있는지 확인
        boolean isDuplicate = recommendedWorkoutRepository.existsByWorkoutIdAndRecommendId(workoutId, recommendationId);
        if (isDuplicate) {
            logger.info("Workout ID {} for recommendation ID {} already exists, skipping.", workoutId, recommendationId);
            continue;
        }

        Workout workout = workoutRepository.findById(workoutId)
            .orElseThrow(() -> new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION));

        RecommendedWorkout recommendedWorkout = new RecommendedWorkout();
        recommendedWorkout.update(
            workoutRecommendation,
            workout,
            weight,
            repeat,
            set,
            caution
        );

        workoutRecommendation.getRws().add(recommendedWorkout);
        recommendedWorkoutRepository.save(recommendedWorkout);

    } catch (NumberFormatException e) {
        logger.error("Error parsing workout ID or numerical values: {}", sentence, e);
    } catch (ArrayIndexOutOfBoundsException e) {
        logger.error("Invalid response format - missing elements: {}", sentence, e);
    } catch (Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage(), e);
    }
}

}

    public WorkoutRecommendation findById(Long recommendationId) {
        return workoutRecommendationRepository.findById(recommendationId);
    }

    public List<WorkoutRecommendation> findAllWithWorkoutRecommendation(int page, Long userId) {
        return workoutRecommendationRepository.findAllWithWorkoutRecommendation(page, userId);
    }

    @Transactional
    public void deleteWorkoutRecommendation(Long workoutRecommendationId) {
        WorkoutRecommendation recommendation = this.findById(workoutRecommendationId);
        workoutRecommendationRepository.delete(recommendation);
    }
}
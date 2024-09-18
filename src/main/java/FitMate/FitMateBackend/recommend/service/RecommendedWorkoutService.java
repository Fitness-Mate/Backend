package FitMate.FitMateBackend.recommend.service;

import FitMate.FitMateBackend.common.exception.errorcodes.RecommendErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.RecommendException;
import FitMate.FitMateBackend.recommend.entity.RecommendedWorkout;
import FitMate.FitMateBackend.recommend.repository.RecommendedWorkoutRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendedWorkoutService {

    private final RecommendedWorkoutRepository recommendedWorkoutRepository;

    @Transactional
    public void save(RecommendedWorkout recommendedWorkout) {
        try {
            recommendedWorkoutRepository.save(recommendedWorkout);
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 다시 던짐
            System.err.println("데이터베이스 저장 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;  // 예외를 다시 던져 트랜잭션 롤백을 유도
        }
    }

    public List<RecommendedWorkout> findById(Long recommendationId) {
        List<RecommendedWorkout> recommendedWorkouts = recommendedWorkoutRepository.findById(recommendationId);
        if(recommendedWorkouts.isEmpty())
            throw new RecommendException(RecommendErrorCode.RECOMMEND_NOT_FOUND_EXCEPTION, 404);

        return recommendedWorkouts;
    }
}

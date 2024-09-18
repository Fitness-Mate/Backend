package FitMate.FitMateBackend.recommend.repository;

import FitMate.FitMateBackend.recommend.entity.RecommendedWorkout;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendedWorkoutRepository {

    private final EntityManager em;

    public void save(RecommendedWorkout recommendedWorkout) {
        em.persist(recommendedWorkout);
				em.flush();  // flush를 통해 강제로 DB에 반영
    }

    public List<RecommendedWorkout> findById(Long recommendationId) {
        return em.createQuery(
                    "select rw from RecommendedWorkout rw" +
                    " join fetch rw.workoutRecommendation wr" +
                    " where wr.id = :id", RecommendedWorkout.class)
                .setParameter("id", recommendationId)
                .getResultList();
    }

		// 운동 중복 여부를 확인하는 메서드 추가
    public boolean existsByWorkoutIdAndRecommendId(Long workoutId, Long recommendId) {
        Long count = em.createQuery(
                    "select count(rw) from RecommendedWorkout rw" +
                    " where rw.workout.id = :workoutId and rw.workoutRecommendation.id = :recommendId", Long.class)
                .setParameter("workoutId", workoutId)
                .setParameter("recommendId", recommendId)
                .getSingleResult();
        return count > 0;
    }
}

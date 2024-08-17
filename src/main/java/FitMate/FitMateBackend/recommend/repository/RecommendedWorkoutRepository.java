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
    }

    public List<RecommendedWorkout> findById(Long recommendationId) {
        return em.createQuery(
                    "select rw from RecommendedWorkout rw" +
                    " join fetch rw.workoutRecommendation wr" +
                    " where wr.id = :id", RecommendedWorkout.class)
                .setParameter("id", recommendationId)
                .getResultList();
    }
}

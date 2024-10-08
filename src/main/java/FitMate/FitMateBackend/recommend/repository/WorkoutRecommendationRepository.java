package FitMate.FitMateBackend.recommend.repository;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.recommend.entity.QWorkoutRecommendation;
import FitMate.FitMateBackend.recommend.entity.WorkoutRecommendation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkoutRecommendationRepository {

    private final EntityManager em;

    public void save(WorkoutRecommendation workoutRecommendation) {
        em.persist(workoutRecommendation);
    }

    public WorkoutRecommendation findById(Long recommendationId) {
        return em.find(WorkoutRecommendation.class, recommendationId);
    }

    public List<WorkoutRecommendation> findAllWithWorkoutRecommendation(int page, Long userId) {
        int offset = (page-1)*ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

        QWorkoutRecommendation workoutRecommendation = QWorkoutRecommendation.workoutRecommendation;
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(workoutRecommendation)
                .from(workoutRecommendation)
                .where(workoutRecommendation.user.id.eq(userId))
                .orderBy(workoutRecommendation.id.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public void delete(WorkoutRecommendation workoutRecommendation) {
        em.remove(workoutRecommendation);
    }
}

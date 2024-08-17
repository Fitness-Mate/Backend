package FitMate.FitMateBackend.admin.workout;

import FitMate.FitMateBackend.workout.entity.QWorkout;
import FitMate.FitMateBackend.workout.entity.Workout;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminWorkoutQueryRepository {

    private final JPAQueryFactory query;

    public List<Workout> getWorkoutList() {
        return query
            .select(QWorkout.workout)
            .from(QWorkout.workout)
            .leftJoin(QWorkout.workout.bodyParts).fetchJoin()
            .leftJoin(QWorkout.workout.machines).fetchJoin()
            .offset(0)
            .limit(10)
            .fetch();
    }
}

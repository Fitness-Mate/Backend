package FitMate.FitMateBackend.admin.workout;

import FitMate.FitMateBackend.workout.entity.Workout;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminWorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findByKoreanNameOrEnglishName(@Param("koreanName") String koreanName, @Param("englishName") String englishName);

}

package FitMate.FitMateBackend.myfit.dto.myWorkout;

import FitMate.FitMateBackend.workout.entity.Workout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyWorkoutSearchResponse {
    private Long workoutId;
    private String workoutName;

    public MyWorkoutSearchResponse(Workout workout) {
        this.workoutId = workout.getId();
        this.workoutName = workout.getKoreanName();
    }
}

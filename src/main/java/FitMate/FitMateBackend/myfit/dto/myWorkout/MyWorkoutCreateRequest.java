package FitMate.FitMateBackend.myfit.dto.myWorkout;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyWorkoutCreateRequest {
    private List<Long> workoutIds = new ArrayList<>();
    private String weight;
    private String rep;
    private String setCount;
}
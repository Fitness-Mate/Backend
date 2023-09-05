package FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyWorkoutUpdateRequest {
    private int myWorkoutIndex;
    private String weight;
    private String rep;
    private String setCount;
}

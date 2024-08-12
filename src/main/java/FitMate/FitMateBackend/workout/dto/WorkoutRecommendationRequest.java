package FitMate.FitMateBackend.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRecommendationRequest {
    private List<String> bodyPartKoreanName;
    private List<String> machineKoreanName;
}

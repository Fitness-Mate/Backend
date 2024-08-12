package FitMate.FitMateBackend.workout.dto;

import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RecommendedWorkoutResponse {
    private LocalDate date;
    private List<String> requestedBodyParts = new ArrayList<>();
    private List<RecommendData> recommends = new ArrayList<>(); //추천 운동

    public RecommendedWorkoutResponse(List<RecommendedWorkout> recommends) {
        for (RecommendedWorkout recommend : recommends) {
            this.recommends.add(new RecommendData(recommend));
        }
        this.date = recommends.get(0).getWorkoutRecommendation().getDate();
        String[] bodyPartsKoreanName = recommends.get(0).getWorkoutRecommendation().getRequestedBodyParts().split(",");
        for (String koreanName : bodyPartsKoreanName) {
            this.requestedBodyParts.add(koreanName);
        }
    }
}
